package com.LearnLC;

import java.util.Stack;

public class isBalancedTree {
    public int height(TreeNode root){
        int heightL,heightR;
        if(root == null) return 0;
        heightL = height(root.left);
        heightR = height(root.right);
        if(heightL == -1 || heightR == -1)
            return -1;
        if(Math.abs(heightL - heightR) < 2)
            return Math.max(heightL,heightR) + 1;
        else
            return -1;
    }
    public boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }
}
