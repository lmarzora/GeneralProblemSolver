package ar.edu.itba.sia.gps;


import ar.edu.itba.sia.gps.api.GPSRule;
import org.jgraph.JGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;


import javax.swing.*;


public class TreePlotter implements GPSObserver{

    private DirectedGraph<GPSNode,GPSRule> graph;
    private JGraph jgraph;
    @Override
    public void start(final GPSNode root) {
        graph = new DefaultDirectedGraph<GPSNode, GPSRule>((node, v1) -> node.getGenerationRule());
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        jgraph = new JGraph(new JGraphModelAdapter(graph));
        frame.getContentPane().add(jgraph);
        frame.setVisible(true);
        graph.addVertex(root);
    }

    @Override
    public void observe(final GPSNode node) {
        graph.addVertex(node);
        graph.addEdge(node,node.getParent());
    }

    @Override
    public void finalize() {

    }
}
