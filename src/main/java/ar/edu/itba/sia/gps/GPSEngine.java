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
		observers.forEach((observer) -> observer.start(root));
		while (frontier.size() > 0) {
			GPSNode n = frontier.remove();
			explored.add(n);

			if (problem.isGoal(n.getState())) {
				observers.forEach(GPSObserver::finalize);
				return n.getPath();
			}

			List<GPSNode> candidates =
					problem.getRules().stream()
							.map((r) -> r.evalRule(n.getState())
									.map((s) -> new GPSNode(s, n, r))
									.orElse(null))
							.filter(Objects::nonNull)
							.filter((node) -> !explored.contains(node))
							.collect(Collectors.toList());

			frontier.addAll(candidates);
			observers.forEach((observer) -> candidates.forEach(observer::observe));
		}

		observers.forEach(GPSObserver::finalize);
		return null;
	}
}