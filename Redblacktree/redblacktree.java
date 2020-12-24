package com.company;


class node<type> {


    public type value;
    public node<type> right = null;
    public node<type> left = null;
    public int color = 1; //0 for black 1 for red
    public node<type> parent = null;

    public final boolean IsLeft() {
        return parent.left == this;
    }

}

public class redblacktree<type> {


    private node<Integer> root;
    private int sizee = 0;

    private void ReColorRed(node<type> Node) {
        if (Node == this.root) {
            Node.color = 0;
            return;
        }
        if (Node.parent.color == 0) {
            return;
        }
        node<type> p = Node.parent;
        node<type> g = p.parent;
        node<type> u = (g.left == p ? g.right : g.left);
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
                node<type> temp = Node.parent;
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

    private void ReColorBlack(node<type> NODE) {
        if (NODE == root) {
            return;
        }
        if (NODE.color != 0) {
            NODE.color = 0;
            return;
        }
        node<type> p = NODE.parent;
        node<type> s = (p.left == NODE ? p.right : p.left);
        node<type> sr = (s != null ? s.right : null);
        node<type> sl = (s != null ? s.left : null);
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
            rotate(NODE, !NODE.IsLeft());
            ReColorBlack(NODE);
            return;
        }
        node<type> FarNode ;
        boolean FarNodeColor ;
        if (slc + src == 2) {
            if (sl.IsLeft() != NODE.IsLeft()) {
                FarNode = sl;
            } else {
                FarNode = sr;
            }
            FarNodeColor = FarNode.color != 0;
        } else {
            if (sl != null) {
                if (sl.IsLeft() != NODE.IsLeft()) {
                    FarNodeColor = true;
                } else {
                    FarNodeColor = false;
                }
                FarNode = sl;
            } else {
                if (sr.IsLeft() != NODE.IsLeft()) {
                    FarNodeColor = true;
                } else {
                    FarNodeColor = false;
                }
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

    private node<type> GetSuc(node<type> NODE) {
        if (NODE.left == null) {
            return NODE;
        }
        return GetSuc(NODE.left);
    }


    private void rotate(node<type> NODE, boolean direct) {
        if (!direct)
            leftRotate(NODE);
        else rightRotate(NODE);
    }

    public void insert(type value) {
        sizee++;
        node<type> Par = new node<type>();
        Par.value = value;
        if (this.root == null) {
            this.root = (node<Integer>) Par;
            this.root.color = 0;
            return;

        }
        Par = (node<type>) getPar(root, (Integer) value);
        //Par become the parent
        node<Integer> CurNode = new node<Integer>();
        CurNode.value = (Integer) value;
        CurNode.parent = (node<Integer>) Par;
        if ((Integer)value < (Integer)Par.value) {
            Par.left = (node<type>) CurNode;
        } else {
            Par.right = (node<type>) CurNode;
        }

        ReColorRed((node<type>) CurNode);

    }
    public void del(Integer val) {

        node<type> CurNode = (node<type>) find((node<Integer>) this.getRoot(), val);
        if (CurNode == null) return;
        sizee--;
        node<type> DelNode;
        if (CurNode.right == null && CurNode.left == null) {
            DelNode = CurNode;
        } else {
            if (CurNode.right != null && CurNode.left != null)
                DelNode = GetPre(CurNode.left);// DelNode = GetSuc(CurNode.right);
            else
                DelNode = (CurNode.right != null ? CurNode.right : CurNode.left);
        }
        CurNode.value = DelNode.value;
        node<type> temp = DelNode;
        node<type> ParTemp = DelNode.parent;
        boolean right = false;
        if (ParTemp.right == temp)
            right = true;
        ReColorBlack(DelNode);
        if (right)
            ParTemp.right = null;
        else ParTemp.left = null;
        DelNode = null;
        System.gc();

    }


    public void Move(node<type> ParNode, node<type> NODE) {
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


    redblacktree() {
        root = null;
    }


    private node<Integer> getPar(node<Integer> NODE, Integer value) {

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


    public node<Integer> find(node<Integer> NODE, Integer value) {
        if (NODE == null)
            return null;
        if (NODE.value == value)
            return NODE;

        return find((value > NODE.value ? NODE.right : NODE.left), value);
    }

    public node<type> getRoot() {
        return (node<type>) root;
    }


    public void print(node<type> NODE) {
        if (NODE == null) return;
        print(NODE.left);
        System.out.print(NODE.value);
        System.out.print(" ");
        print(NODE.right);

    }

    public int size() {
        return this.sizee;
    }


    private void clear(node<type> NODE) {
        if (NODE == null) return;
        clear(NODE.left);
        clear(NODE.right);
        NODE = null;
        System.gc();
    }


    public void clear() {
        this.clear((node<type>) this.root);
        root = null;
        sizee = 0;
    }


    private node<type> GetPre(node<type> NODE) {
        if (NODE.right == null)
            return NODE;
        return GetSuc(NODE.right);
    }

    void leftRotate(node<type> NODE) {
        node<type> ParNode = NODE.right;
        if (NODE == root)
            root = (node<Integer>) ParNode;
        Move(ParNode, NODE);
        NODE.right = ParNode.left;
        // connect new parent's left element with node
        if (ParNode.left != null)
            ParNode.left.parent = NODE;
        // connect new parent with NODE
        ParNode.left = NODE;

    }


    private void rightRotate(node<type> NODE) {
        // new parent will be node's left child
        node<type> ParNode = NODE.left;
        if (NODE == root)
            root = (node<Integer>) ParNode;
        Move(ParNode, NODE);
        NODE.left = ParNode.right;
        if (ParNode.right != null)
            ParNode.right.parent = NODE;
        ParNode.right = NODE;

    }
}