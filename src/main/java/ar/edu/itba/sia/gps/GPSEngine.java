package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.gps.api.*;
import java.util.*;
import java.util.stream.Collectors;

public class GPSEngine {

	private Queue<GPSNode> frontier;
	private Set<GPSNode> explored;
	private GPSProblem problem;
	private List<GPSObserver> observers;

	public GPSEngine(GPSProblem myProblem, SearchStrategy myStrategy, int cutCondition) {
		this.problem = myProblem;
		this.observers = new ArrayList<>();
		Comparator<GPSNode> comparator = null;
		switch (myStrategy) {
			case ASTAR:
				comparator = Comparator.comparingInt((n) -> n.getCost() + problem.getHValue(n.getState()));
				break;
			case BFS:
				comparator = Comparator.comparingInt(GPSNode::getHeight);
				break;
			case DFS:
				comparator = Comparator.comparingInt(GPSNode::getHeight).reversed();
				break;
			case GREEDY:
				comparator = Comparator.comparingInt((n) -> problem.getHValue(n.getState()));
				break;
			case IDDFS:
				comparator = Comparator.comparingInt((GPSNode n) -> n.getHeight()%cutCondition).reversed();
				break;
		}
		frontier = new PriorityQueue<>(comparator);
		explored = new HashSet<>();
	}

	public List<GPSRule> findSolution() {
		GPSNode root = new GPSNode(problem.getInitState());
		frontier.add(root);
		observers.forEach(observer -> observer.start(root));
		List<GPSRule> rules;
		long seed;
		while (frontier.size() > 0) {
			GPSNode n = frontier.remove();
			if(explored.contains(n))
				continue;
			explored.add(n);
			observers.forEach(o -> o.observeVisited(n));
			if (problem.isGoal(n.getState())) {
				observers.forEach(o -> o.finalize());
				return n.getPath();
			}
			seed = System.nanoTime();
			rules = problem.getRules();
			Collections.shuffle(rules, new Random(seed));

			List<GPSNode> candidates =
					   rules.stream()
							.map((r) -> r.evalRule(n.getState())
									.map((s) -> new GPSNode(s, n, r))
									.orElse(null))
							.filter(Objects::nonNull)
							.filter((node) -> !explored.contains(node))
							.collect(Collectors.toList());

			frontier.addAll(candidates);
			observers.forEach(o -> candidates.forEach(c -> o.observeFrontier(c)));
		}

		observers.forEach(o -> o.finalize());
		return null;
	}

	public GPSEngine addObserver(GPSObserver observer) {
		observers.add(observer);
		return this;
	}
}