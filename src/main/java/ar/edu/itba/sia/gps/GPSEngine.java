package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.gps.api.*;

import java.util.*;


public class GPSEngine {

	private Queue<GPSNode> frontier;
	private Set<GPSNode> explored;

	public GPSEngine(GPSProblem myProblem, SearchStrategy myStrategy) {
		// TODO: frontier = *Su queue favorito, TENIENDO EN CUENTA EL ORDEN DE LOS NODOS*
		Comparator<GPSNode> comparator;
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
				break;
		}

	}

	public void findSolution() {

	}

//	private void explode(GPSNode node) {
//		Collection<GPSNode> newCandidates;
//		switch (strategy) {
//		case BFS:
//			if (explored.containsKey(node.getState())) {
//				return;
//			}
//			newCandidates = new ArrayList<>();
//			addCandidates(node, newCandidates);
//			// TODO: ¿Cómo se agregan los nodos a frontier en BFS?
//			break;
//		case DFS:
//			if (explored.containsKey(node.getState())) {
//				return;
//			}
//			newCandidates = new ArrayList<>();
//			addCandidates(node, newCandidates);
//			// TODO: ¿Cómo se agregan los nodos a frontier en DFS?
//			break;
//		case IDDFS:
//			if (explored.containsKey(node.getState())) {
//				return;
//			}
//			newCandidates = new ArrayList<>();
//			addCandidates(node, newCandidates);
//			// TODO: ¿Cómo se agregan los nodos a frontier en IDDFS?
//			break;
//		case GREEDY:
//			newCandidates = new PriorityQueue<>(/* TODO: Comparator! */);
//			addCandidates(node, newCandidates);
//			// TODO: ¿Cómo se agregan los nodos a frontier en GREEDY?
//			break;
//		case ASTAR:
//			if (!isBest(node.getState(), node.getCost())) {
//				return;
//			}
//			newCandidates = new ArrayList<>();
//			addCandidates(node, newCandidates);
//			// TODO: ¿Cómo se agregan los nodos a frontier en A*?
//			break;
//		}
//	}
//
//	private void addCandidates(GPSNode node, Collection<GPSNode> candidates) {
//		explosionCounter++;
//		explored.put(node.getState(), node.getCost());
//		for (GPSRule rule : problem.getRules()) {
//			Optional<GPSState> newState = rule.evalRule(node.getState());
//			if (newState.isPresent()) {
//				GPSNode newNode = new GPSNode(newState.get(), node.getCost() + rule.getCost());
//				newNode.setParent(node);
//				candidates.add(newNode);
//			}
//		}
//	}
//
//	private boolean isBest(GPSState state, Integer cost) {
//		return !explored.containsKey(state) || cost < explored.get(state);
//	}

	// GETTERS FOR THE PEOPLE!

	public Queue<GPSNode> getFrontier() {
		return frontier;
	}
/*
	public Map<GPSState, Integer> getExplored() {
		return explored;
	}


	public GPSProblem getProblem() {
		return problem;
	}

	public long getExplosionCounter() {
		return explosionCounter;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isFailed() {
		return failed;
	}

	public GPSNode getSolutionNode() {
		return solutionNode;
	}
*/


}
