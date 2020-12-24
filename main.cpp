#include <iostream>
#include "redblacktree.cpp"

int main() {

    redblacktree<int> RBT;
    RBT.insert(1);
    RBT.insert(2);
    RBT.insert(3);
    RBT.insert(4);
    RBT.insert(5);
    RBT.insert(2);
    RBT.insert(2);
    RBT.insert(6);
    RBT.insert(5);
    RBT.insert(5);
    RBT.print(RBT.getRoot());
    cout << endl;
    cout << RBT.size();
    cout << endl;
    RBT.del(5);
    RBT.del(6);
    RBT.del(7);
    RBT.del(2);
    RBT.del(2);
    RBT.del(5);
    cout << RBT.size() << endl;
    RBT.print(RBT.getRoot());
    cout<<endl;
    RBT.clear();
    cout<<RBT.size()<<endl;
    RBT.print(RBT.getRoot());
    cout << endl;

}
