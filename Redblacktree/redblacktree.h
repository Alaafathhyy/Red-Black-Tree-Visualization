//
// Created by Alaa on 12/21/2020.
//

#ifndef UNTITLED3_REDBLACKTREEE_H
#define UNTITLED3_REDBLACKTREEE_H

#include"node.h"

template<class type>
class redblacktree {
private :
    node<type> *root;
    int sizee = 0;

    void ReColorRed(node<type> *NODE);

    void ReColorBlack(node<type> *NODE);

    node<type> *GetSuc(node<type> *NODE);

    node<type> *GetPre(node<type> *NODE);

    void Move(node<type> *nParent, node<type> *NODE);

    void leftRotate(node<type> *NODE);

    void rightRotate(node<type> *NODE);
    void print(node<type> *Node);

    void rotate(node<type> *Node, bool direct); // direct is 0 if  rotate left else right
    node<type> *getPar(node<type> *nodee, type val);

public:
    redblacktree();

    int size();

    void insert(type val);

    void del(type val);


    void clear();
    void print();
    node<type> *find(node<type> *node, type value);

    void clear(node<type> *NODE);

    node<type> *getRoot();
};


#endif //UNTITLED3_REDBLACKTREEE_H
