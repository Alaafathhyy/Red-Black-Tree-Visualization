import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;

public class Form {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton btnInsert;
    private JButton btnErase;
    private JButton btnClear;
    private JPanel tablet;

    private RedBlackTree myRedBlackTree;
    public Form() {
        btnInsert.addActionListener(actionEvent -> btnInsert_Click());
        btnErase.addActionListener(actionEvent -> btnErase_Click());
        btnClear.addActionListener(actionEvent -> btnClear_Click());

        myRedBlackTree = new RedBlackTree();
    }

    private void btnClear_Click() {
        ///processing
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void btnErase_Click() {
        ///processing
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void btnInsert_Click() {
        ///processing
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void drawGraph(Forest<Node, Integer> graph) {

        Transformer<Node, Paint> vertexColor = myNode -> myNode.color;
        Transformer<Node, String> vertexText = myNode -> String.valueOf(myNode.num);

        Layout<Node, Integer> layout = new TreeLayout<>(graph);
        VisualizationViewer<Node, Integer> vv = new VisualizationViewer<>(layout);

        vv.getRenderContext().setVertexLabelTransformer(vertexText);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);

        final DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<>();
        vv.setGraphMouse(graphMouse);
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

        tablet.removeAll();
        tablet.add(vv);
        tablet.revalidate();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
