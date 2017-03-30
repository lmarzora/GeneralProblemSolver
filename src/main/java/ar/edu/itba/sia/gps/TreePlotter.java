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
        String nodeId = node.getId();
        String parentId = node.getParent().getId();
        String edgeId = node.getGenerationRule().getName() + " (" + parentId + ", " + nodeId + ")";
        graph.addNode(node.getId());
        graph.getNode(nodeId).setAttribute("name",node.getId());
        graph.addEdge(edgeId,parentId,nodeId);
        graph.getEdge(edgeId).setAttribute("rule",node.getGenerationRule().getName());
    }

    @Override
    public void finalize() {
        graph.display();
    }
}
