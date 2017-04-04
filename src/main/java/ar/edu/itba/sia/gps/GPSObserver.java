package ar.edu.itba.sia.gps;

public interface GPSObserver {

	void start(final GPSNode root);
    void observeFrontier(final GPSNode node);
    void observeVisited(final GPSNode node);
    void finalize();
}
