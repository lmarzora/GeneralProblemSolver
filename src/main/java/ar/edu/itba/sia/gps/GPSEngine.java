package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.gps.api.*;
import java.util.*;
import java.util.stream.Collectors;

public class GPSEngine {

	private Queue<GPSNode> frontier;
	private Set<GPSNode> explored;
	private GPSProblem problem;

	public GPSEngine(GPSProblem myProblem, SearchStrategy myStrategy) {
		this.problem = myProblem;
		Comparator<GPSNode> comparator = null;
		switch (myStrategy) {
			case ASTAR:
				comparator = Comparator.comparingInt((n) -> n.getCost() + myProblem.getHValue(n.getState()));
				break;
			case BFS:
				comparator = Comparator.comparingInt(GPSNode::getHeight);
				break;
			case DFS:
				comparator = Comparator.comparingInt(GPSNode::getHeight).reversed();
				break;
			case GREEDY:
				comparator = Comparator.comparingInt((n) -> myProblem.getHValue(n.getState()));
				break;
			case IDDFS:
				comparator = Comparator.comparingInt((GPSNode n) -> n.getHeight()%5).reversed();
				break;
		}
		frontier = new PriorityQueue<>(comparator);
		frontier.add(new GPSNode(myProblem.getInitState()));
		explored = new HashSet<>();
	}

	public List<GPSRule> findSolution() {
		while (frontier.size() > 0) {
			GPSNode n = frontier.remove();
			explored.add(n);
			if (problem.isGoal(n.getState()))
				return n.getPath();

			List<GPSNode> candidates =
					problem.getRules().stream()
							.map((r) -> r.evalRule(n.getState())
									.map((s) -> new GPSNode(s, n, r))
									.orElse(null))
							.filter(Objects::nonNull)
							.filter((node) -> !explored.contains(node))
							.collect(Collectors.toList());

			frontier.addAll(candidates);
		}

		return null;
	}
}