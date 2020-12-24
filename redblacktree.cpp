//
// Created by Hager on 12/21/2020.
//
#include <bits/stdc++.h>
#include "redblacktree.h"
#include"node.h"

using namespace std;

template<class type>
void redblacktree<type>::leftRotate(node<type> *NODE) {
    node<type> *ParNode = NODE->right;
    if (NODE == root)
        root = ParNode;
    Move(ParNode, NODE);
    NODE->right = ParNode->left;
    // connect new parent's left element with node
    if (ParNode->left != NULL)
        ParNode->left->parent = NODE;
    // connect new parent with NODE
    ParNode->left = NODE;

}

template<class type>
void redblacktree<type>::rightRotate(node<type> *NODE) {
    // new parent will be node's left child
    node<type> *ParNode = NODE->left;
    if (NODE == root)
        root = ParNode;
    Move(ParNode, NODE);
    NODE->left = ParNode->right;
    if (ParNode->right != NULL)
        ParNode->right->parent = NODE;
    ParNode->right = NODE;

}

template<class type>
void redblacktree<type>::insert(type value) {
    sizee++;
    auto *Par = new node<type>;
    Par->value = value;
    if (this->root == NULL) {
        this->root = Par;
        this->root->color = 0;
        return;

    }
    Par = getPar(root, value);
    //Par become the parent
    auto *CurNode = new node<type>;
    CurNode->value = value;
    CurNode->parent = Par;
    if (value < Par->value) {
        Par->left = CurNode;
    } else {
        Par->right = CurNode;
    }

    ReColorRed(CurNode);

}

template<class type>
void redblacktree<type>::ReColorRed(node<type> *Node) {
    if (Node == this->root) {
        Node->color = 0;
        return;
    }
    if (Node->parent->color == 0) {
        return;
    }
    auto p = Node->parent;
    auto g = p->parent;
    auto u = (g->left == p ? g->right : g->left);
    int uc = (u == NULL ? 0 : u->color);
    if (uc && p->color) {
        u->color = 0;
        p->color = 0;
        g->color = 1;
        ReColorRed(g);
        return;
    }
    bool left = 0, right = 1;

    if (Node->parent->IsLeft()) {
        // parent is left and node is left
        if (Node->IsLeft()) {
            // right rotation of grandfather , swap colors of grandfather
            swap(p->color, g->color);
            rotate(g, right);

        }
            // parent is left and node is right
        else {

            rotate(p, left);
            ReColorRed(Node->left);
        }

        return;

    }
    if (!Node->parent->IsLeft()) {
        // parent is right and node is left
        if (Node->IsLeft()) {
            // right rotate on p then left on g
            auto temp = Node->parent;
            rotate(Node->parent, 1);
            ReColorRed(temp);

        }
            // parent is right and node is right
        else {
            // left rotation of grandfather , swap colors of grandfather
            swap(p->color, g->color);
            rotate(g, 0);
        }


    }

}

template<class type>
void redblacktree<type>::rotate(node<type> *NODE, bool direct) {
    if (!direct)
        leftRotate(NODE);
    else rightRotate(NODE);
}


template<class type>
void redblacktree<type>::del(type val) {

    auto CurNode = find(this->getRoot(), val);
    if (CurNode == NULL) return;
    sizee--;
    node<type> *DelNode;
    if (CurNode->right == NULL && CurNode->left == NULL) {
        DelNode = CurNode;
    } else {
        if (CurNode->right != NULL && CurNode->left != NULL)
            DelNode = GetPre(CurNode->left);// DelNode = GetSuc(CurNode->right);
        else
            DelNode = (CurNode->right != NULL ? CurNode->right : CurNode->left);
    }
    CurNode->value = DelNode->value;
    auto temp = DelNode;
    auto ParTemp = DelNode->parent;
    bool right = 0;
    if (ParTemp->right == temp)
        right = 1;
    ReColorBlack(DelNode);
    if (right)
        ParTemp->right = NULL;
    else ParTemp->left = NULL;
    delete DelNode;

}

template<class type>
void redblacktree<type>::ReColorBlack(node<type> *NODE) {
    if (NODE == root)
        return;
    if (NODE->color) {
        NODE->color = 0;
        return;
    }
    auto p = NODE->parent;
    auto s = (p->left == NODE ? p->right : p->left);
    auto sr = s->right, sl = s->left;
    bool src = (sr != NULL ? sr->color : 0),
            slc = (sl != NULL ? sl->color : 0), sc = (s != NULL ? s->color : 0);
    if (slc + src + sc == 0) {
        if (s != NULL)
            s->color = 1;
        ReColorBlack(p);

        return;
    }
    if (sc) {
        swap(s->color, p->color);
        rotate(NODE, !NODE->IsLeft());
        ReColorBlack(NODE);
        return;
    }
    node<type> *FarNode = nullptr;
    bool FarNodeColor = 0;
    if (slc + src == 2) {
        if (sl->IsLeft() != NODE->IsLeft())
            FarNode = sl;
        else FarNode = sr;
        FarNodeColor = FarNode->color;
    } else {
        if (sl != NULL) {
            if (sl->IsLeft() != NODE->IsLeft())
                FarNodeColor = 1;
            else FarNodeColor = 0;
            FarNode = sl;
        } else {
            if (sr->IsLeft() != NODE->IsLeft())
                FarNodeColor = 1;
            else FarNodeColor = 0;
            FarNode = sr;
        }

    }
    if (FarNodeColor) {
        FarNode->color = 0;
        swap(p->color, s->color);
        rotate(p, !NODE->IsLeft());
    } else {
        swap(FarNode->color, s->color);
        rotate(s, NODE->IsLeft());
        ReColorBlack(NODE);
    }


}

template<class type>
void redblacktree<type>::Move(node<type> *ParNode, node<type> *NODE) {
    if (NODE->parent != NULL) {
        if (NODE->IsLeft()) {
            NODE->parent->left = ParNode;
        } else {
            NODE->parent->right = ParNode;
        }
    }
    ParNode->parent = NODE->parent;
    NODE->parent = ParNode;
}


template<class type>
redblacktree<type>::redblacktree() {
    root = NULL;
}

template<class type>
node<type> *redblacktree<type>::getPar(node<type> *NODE, type value) {
    if (NODE->value <= value) {
        if (NODE->right == NULL)
            return NODE;
        else getPar(NODE->right, value);
    } else {
        if (NODE->left == NULL)
            return NODE;
        else getPar(NODE->left, value);
    }
}

template<class type>
node<type> *redblacktree<type>::GetSuc(node<type> *NODE) {
    if (NODE->left == NULL)
        return NODE;
    return GetSuc(NODE->left);
}

template<class type>
node<type> *redblacktree<type>::find(node<type> *NODE, type value) {
    if (NODE == NULL)
        return NULL;
    if (NODE->value == value)
        return NODE;
    return find((value > NODE->value ? NODE->right : NODE->left), value);
}

template<class type>
node<type> *redblacktree<type>::getRoot() {
    return root;
}

template<class type>
void redblacktree<type>::print(node<type> *NODE) {
    if (NODE == NULL) return;
    print(NODE->left);
    cout << NODE->value << " ";
    print(NODE->right);

}

template<class type>
int redblacktree<type>::size() {
    return this->sizee;
}


template<class type>
void redblacktree<type>::clear(node<type> *NODE) {
    if (NODE == NULL) return;
    clear(NODE->left);
    clear(NODE->right);
    delete NODE;
}

template<class type>
void redblacktree<type>::clear() {
    this->clear(this->root);
    root = NULL, sizee = 0;
}

template<class type>
node<type> *redblacktree<type>::GetPre(node<type> *NODE) {
    if (NODE->right == NULL)
        return NODE;
    return GetSuc(NODE->right);
}
















