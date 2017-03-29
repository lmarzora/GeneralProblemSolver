package ar.edu.itba.sia.gps;

/**
 * Created by lumarzo on 29/03/17.
 */
public interface GPSObserver {
    void start(final GPSNode root);
    void observe(final GPSNode node);
    void finalize();
}
