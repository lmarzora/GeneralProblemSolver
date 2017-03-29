package ar.edu.itba.sia.gps;


import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;



public class TreePlotter implements GPSObserver{

    private Graph graph;
    
    @Override
    public void start(final GPSNode root) {
        graph = new SingleGraph("graph");
        graph.addNode(root.getId());
    }

    @Override
    public void observe(final GPSNode node) {
        graph.addNode(node.getId());
        graph.addEdge(node.getGenerationRule().getName(),node.getParent().getId(),node.getId());
    }

    @Override
    public void finalize() {
        graph.display();
    }
}
