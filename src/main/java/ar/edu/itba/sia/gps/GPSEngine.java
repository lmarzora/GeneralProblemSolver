package gps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public abstract class GPSEngine {

	protected Queue<GPSNode> open;
	protected Map<GPSState, Integer> bestCosts;

	protected GPSProblem problem;
	long explosionCounter;

	// Use this variable in open set order.
	protected SearchStrategy strategy;

	public GPSEngine(GPSProblem myProblem, SearchStrategy myStrategy) {
		// TODO: open = *Su queue favorito, TENIENDO EN CUENTA EL ORDEN DE LOS NODOS*
		bestCosts = new HashMap<GPSState, Integer>();
		problem = myProblem;
		strategy = myStrategy;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		explosionCounter = 0;
		boolean finished = false;
		boolean failed = false;
		open.add(rootNode);
		// TODO: ¿Lógica de IDDFS?
		while (!failed && !finished) {
			if (open.size() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = open.remove();
				if (problem.isGoal(currentNode.getState())) {
					finished = true;
					System.out.println(currentNode.getSolution());
					System.out.println("Expanded nodes: " + explosionCounter);
					System.out.println("Solution cost: " + currentNode.getCost());
				} else {
					explode(currentNode);
				}
			}
		}
		if (finished) {
			System.out.println("OK! solution found!");
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
		}
	}

	private void explode(GPSNode node) {
		Collection<GPSNode> newCandidates;
		switch (strategy) {
		case ASTAR:
			if (!isBest(node.getState(), node.getCost())) {
				return;
			}
			newCandidates = new ArrayList<>();
			break;
		case BFS:
		case DFS:
		case IDDFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			break;
		case GREEDY:
			newCandidates = new TreeSet<>(/* TODO: Comparator! */);
			break;
		default:
			newCandidates = new ArrayList<>();
		}
		explosionCounter++;
		updateBest(node);
		for (GPSRule rule : problem.getRules()) {
			try {
				GPSNode newNode = new GPSNode(rule.evalRule(node.getState()), node.getCost() + rule.getCost());
				newNode.setParent(node);
				newCandidates.add(newNode);
			} catch (NotAppliableException e) {
				// Si no es aplicable, se saltea.
			}
		}
		// TODO: ¿Cómo se agregan los nodos en las diferentes estrategias?
		switch (strategy) {
		case ASTAR:
			break;
		case BFS:
			break;
		case DFS:
			break;
		case IDDFS:
			break;
		case GREEDY:
			break;
		}
	}

	private boolean isBest(GPSState state, Integer cost) {
		return !bestCosts.containsKey(state) || cost < bestCosts.get(state);
	}

	private void updateBest(GPSNode node) {
		bestCosts.put(node.getState(), node.getCost());
	}

}
