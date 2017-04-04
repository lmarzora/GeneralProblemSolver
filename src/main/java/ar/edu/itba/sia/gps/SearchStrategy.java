package ar.edu.itba.sia.gps;

public enum SearchStrategy {
    BFS, DFS, IDDFS, GREEDY, ASTAR;
	
	public static boolean contains(final String st) {

	    for (SearchStrategy s : SearchStrategy.values()) {
	        if (s.name().equals(st)) 
	            return true;
	    }
	    return false;
	}
}
