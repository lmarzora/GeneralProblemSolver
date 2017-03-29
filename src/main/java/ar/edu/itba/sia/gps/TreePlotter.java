package ar.edu.itba.sia.gps;


import ar.edu.itba.sia.gps.api.GPSRule;
import org.jgraph.JGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


import javax.swing.*;


public class TreePlotter implements GPSObserver{

    private DirectedGraph<GPSNode,DefaultEdge> graph;


    @Override
    public void start(final GPSNode root) {
        graph = new DefaultDirectedGraph<GPSNode, DefaultEdge>(DefaultEdge.class);
        graph.addVertex(root);
    }

    @Override
    public void observe(final GPSNode node) {
        graph.addVertex(node);
        graph.addEdge(node.getParent(),node);
    }

    @Override
    public void finalize() {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        JGraph jgraph = new JGraph(new JGraphModelAdapter(graph));
        frame.getContentPane().add(jgraph);
        frame.setVisible(true);

    }
}
