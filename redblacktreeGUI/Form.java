package com.company;

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
    private JTextField Mytext;
    private JButton btnInsert;
    private JButton btnErase;
    private JButton btnClear;
    private JPanel tablet;

    private final RedBlackTree myRedBlackTree;

    public Form() {
        btnInsert.addActionListener(actionEvent -> btnInsert_Click());
        btnErase.addActionListener(actionEvent -> btnErase_Click());
        btnClear.addActionListener(actionEvent -> btnClear_Click());

        myRedBlackTree = new RedBlackTree();
    }

    private void btnClear_Click() {

        myRedBlackTree.clear();
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void btnErase_Click() {
        String s = Mytext.getText();
        int x = Integer.parseInt(s);
        myRedBlackTree.del(x);
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void btnInsert_Click() {

        String s = Mytext.getText();
        int x = Integer.parseInt(s);
        myRedBlackTree.insert(x);
        drawGraph(myRedBlackTree.buildGraph());
    }

    private void drawGraph(Forest<node, Integer> graph) {

        Transformer<node, Paint> vertexColor = (mynode -> mynode.color == 1 ? Color.RED : Color.black);
        Transformer<node, String> vertexText = mynode -> String.valueOf(mynode.value);

        Layout<node, Integer> layout = new TreeLayout<>(graph);
        VisualizationViewer<node, Integer> vv = new VisualizationViewer<>(layout);

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
