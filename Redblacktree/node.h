
#ifndef UNTITLED3_NODE_H
#define UNTITLED3_NODE_H

template<class type>
class node {
public:
    type value;
    node<type> *right = NULL;
    node<type> *left = NULL;
    int color = 1;//0 for black 1 for red
    node<type> *parent = NULL;
    bool IsLeft()
    {
        return parent->left== this;
    }

};


#endif //UNTITLED3_NODE_H
