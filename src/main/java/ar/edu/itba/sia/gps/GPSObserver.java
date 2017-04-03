package ar.edu.itba.sia.gps;

/**
 * Created by lumarzo on 29/03/17.
 */
public interface GPSObserver {
    void start(final GPSNode root);
    void observeFrontier(final GPSNode node);
    void observeVisited(final GPSNode node);
    void finalize();
}
