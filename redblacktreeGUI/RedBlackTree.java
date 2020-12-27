package com.company;

import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;

class node {


    public int value;
    public node right = null;
    public node left = null;
    public int color = 1; //0 for black 1 for red
    public node parent = null;

    public final boolean IsLeft() {
        return parent.left == this;
    }

}

public class RedBlackTree {
    private static node root;
    private static DelegateForest<node, Integer> graph;
    private static int cnt;

    static void build(node n) {
        if (n == null) return;
        if (n.left != null) {
            build(n.left);
            graph.addEdge(cnt++, n, n.left);
        }
        if (n.right != null) {
            build(n.right);
            graph.addEdge(cnt++, n, n.right);
        }
        graph.addVertex(n);

    }

    static Forest<node, Integer> buildGraph() {
        graph = new DelegateForest<>();
        cnt = 0;
        build(root);
        return graph;
    }


    private void ReColorRed(node Node) {
        if (Node == root) {
            Node.color = 0;
            return;
        }
        if (Node.parent.color == 0) {
            return;
        }
        node p = Node.parent;
        node g = p.parent;
        node u = (g.left == p ? g.right : g.left);
        int uc = (u == null ? 0 : u.color);
        if (uc != 0 && p.color != 0) {
            u.color = 0;
            p.color = 0;
            g.color = 1;

            ReColorRed(g);
            return;
        }
        boolean left = false;
        boolean right = true;

        if (Node.parent.IsLeft()) {
            // parent is left and node is left
            if (Node.IsLeft()) {
                int temp = p.color;
                p.color = g.color;
                g.color = temp;

                rotate(g, right);

            } else {

                rotate(p, left);
                ReColorRed(Node.left);
            }

            return;

        }
        if (!Node.parent.IsLeft()) {
            // parent is right and node is left
            if (Node.IsLeft()) {
                // right rotate on p then left on g
                node temp = Node.parent;
                rotate(Node.parent, true);

                ReColorRed(temp);

            }
            // parent is right and node is right
            else {
                // left rotation of grandfather , swap colors of grandfather
                int temp = p.color;
                p.color = g.color;
                g.color = temp;

                rotate(g, false);
            }


        }

    }

    private void ReColorBlack(node NODE) {
        if (NODE == root) {
            return;
        }
        if (NODE.color != 0) {
            NODE.color = 0;
            return;
        }
        node p = NODE.parent;
        node s = (p.left == NODE ? p.right : p.left);
        node sr = (s != null ? s.right : null);
        node sl = (s != null ? s.left : null);
        int src = (sr != null ? sr.color : 0);
        int slc = (sl != null ? sl.color : 0);
        int sc = (s != null ? s.color : 0);
        if (slc + src + sc == 0) {
            if (s != null) {
                s.color = 1;
            }

            ReColorBlack(p);

            return;
        }
        if (sc == 1) {
            int temp = p.color;
            p.color = s.color;
            s.color = temp;
            rotate(p, !NODE.IsLeft());
            ReColorBlack(NODE);
            return;
        }
        node FarNode;
        boolean FarNodeColor;
        if (slc + src == 2) {
            assert sl != null;
            if (sl.IsLeft() != NODE.IsLeft()) {
                FarNode = sl;
            } else {
                FarNode = sr;
            }
            FarNodeColor = FarNode.color != 0;
        } else {
            if (sl != null) {
                FarNodeColor = sl.IsLeft() != NODE.IsLeft();
                FarNode = sl;
            } else {
                assert sr != null;
                FarNodeColor = sr.IsLeft() != NODE.IsLeft();
                FarNode = sr;
            }

        }
        if (FarNodeColor) {
            FarNode.color = 0;
            int temp = p.color;
            p.color = s.color;
            s.color = temp;

            rotate(p, !NODE.IsLeft());
        } else {
            int temp = FarNode.color;
            FarNode.color = s.color;
            s.color = temp;
            rotate(s, NODE.IsLeft());
            ReColorBlack(NODE);
        }

    }

    private node GetSuc(node NODE) {
        if (NODE.left == null) {
            return NODE;
        }
        return GetSuc(NODE.left);
    }


    private void rotate(node NODE, boolean direct) {
        if (!direct)
            leftRotate(NODE);
        else rightRotate(NODE);
    }

    public void insert(int value) {

        node Par = new node();
        Par.value = value;
        if (root == null) {
            root = Par;
            root.color = 0;
            return;

        }
        Par = getPar(root, value);
        //Par become the parent
        node CurNode = new node();
        CurNode.value = value;
        CurNode.parent = Par;
        if ((Integer) value < (Integer) Par.value) {
            Par.left = CurNode;
        } else {
            Par.right = CurNode;
        }

        ReColorRed(CurNode);

    }

    public void del(Integer val) {

        node CurNode = find(this.getRoot(), val);
        if (CurNode == null) return;

        node DelNode;
        if (CurNode.right == null && CurNode.left == null) {
            DelNode = CurNode;
        } else {
            if (CurNode.right != null && CurNode.left != null)
                DelNode = GetPre(CurNode.left);// DelNode = GetSuc(CurNode.right);
            else
                DelNode = (CurNode.right != null ? CurNode.right : CurNode.left);
        }
        if (DelNode == root) {
            root = null;
            return;
        }
        CurNode.value = DelNode.value;
        if (DelNode.right != null || DelNode.left != null) {
            DelNode.value = (DelNode.left == null ? DelNode.right.value : DelNode.left.value);
            ReColorBlack((DelNode.left == null ? DelNode.right : DelNode.left));
            if (DelNode.left != null)
                DelNode.left = null;
            else DelNode.right = null;
            DelNode.left = DelNode.right = null;

            return;
        }
        node temp = DelNode;
        node ParTemp = DelNode.parent;
        boolean right = false;
        if (ParTemp.right == temp)
            right = true;
        ReColorBlack(DelNode);
        if (right)
            ParTemp.right = null;
        else ParTemp.left = null;
        DelNode = null;


    }


    public void Move(node ParNode, node NODE) {
        if (NODE.parent != null) {
            if (NODE.IsLeft()) {
                NODE.parent.left = ParNode;
            } else {
                NODE.parent.right = ParNode;
            }
        }
        ParNode.parent = NODE.parent;
        NODE.parent = ParNode;
    }


    RedBlackTree() {
        root = null;
    }


    private node getPar(node NODE, Integer value) {

        if (NODE.value <= value) {
            if (NODE.right == null)
                return NODE;
            else return getPar(NODE.right, value);
        } else {
            if (NODE.left == null)
                return NODE;
            else return getPar(NODE.left, value);
        }
    }


    public node find(node NODE, Integer value) {
        if (NODE == null)
            return null;
        if (NODE.value == value)
            return NODE;

        return find((value > NODE.value ? NODE.right : NODE.left), value);
    }

    public node getRoot() {
        return root;
    }


    private void clear(node NODE) {
        if (NODE == null) return;
        clear(NODE.left);
        clear(NODE.right);
        NODE = null;
        System.gc();
    }

    public void clear() {
        this.clear(root);
        root = null;
    }

    private node GetPre(node NODE) {
        if (NODE.right == null)
            return NODE;
        return GetPre(NODE.right);
    }

    void leftRotate(node NODE) {
        node ParNode = NODE.right;
        if (NODE == root)
            root = ParNode;
        Move(ParNode, NODE);
        NODE.right = ParNode.left;
        // connect new parent's left element with node
        if (ParNode.left != null)
            ParNode.left.parent = NODE;
        // connect new parent with NODE
        ParNode.left = NODE;

    }


    private void rightRotate(node NODE) {
        // new parent will be node's left child
        node ParNode = NODE.left;
        if (NODE == root)
            root = ParNode;
        Move(ParNode, NODE);
        NODE.left = ParNode.right;
        if (ParNode.right != null)
            ParNode.right.parent = NODE;
        ParNode.right = NODE;

    }
}
