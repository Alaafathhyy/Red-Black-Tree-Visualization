package com.company;

public class Main {

    public static void main(String[] args) {

        redblacktree<Integer> RBT=new redblacktree<Integer>();
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
		System.out.print("\n");
		System.out.print(RBT.size());
		System.out.print("\n");
		RBT.del(5);
		RBT.del(6);
		RBT.del(7);
		RBT.del(2);
		RBT.del(2);
		RBT.del(5);
		System.out.print(RBT.size());
		System.out.print("\n");
		RBT.print(RBT.getRoot());
		System.out.print("\n");
		RBT.clear();
		System.out.print(RBT.size());
		System.out.print("\n");
		RBT.print(RBT.getRoot());
		System.out.print("\n");

    }
}
