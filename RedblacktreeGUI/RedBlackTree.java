import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;

import java.awt.*;

public class RedBlackTree {
    Forest<Node, Integer> buildGraph(){
        Node a = new Node(1, Color.BLACK);
        Node b = new Node(2, Color.BLACK);
        Node c = new Node(3, Color.RED);
        Node d = new Node(4, Color.RED);

        Forest<Node, Integer> graph = new DelegateForest<>();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(0, a, b);
        graph.addEdge(1, a, c);
        graph.addEdge(2, b, d);

        return graph;
    }
}
