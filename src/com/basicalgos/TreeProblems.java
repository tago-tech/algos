package com.basicalgos;

import com.datastructure.TreeNode;

public class TreeProblems {

    /**
     * 二叉树最近公共祖先
     * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //在root树中找 节点 p 和 q
        if (root == null) return null;
        //若当前节点为p 或者 q
        if (root == p || root == q) {
            return root;
        }
        //查看子结点上是否有另一个节点
        TreeNode l = lowestCommonAncestor(root.left,p,q);
        TreeNode r = lowestCommonAncestor(root.right,p,q);
        if (l != null && r != null) {
            return root;
        }
        //返回其中一个不为null的
        if (l != null || r != null) {
            return l == null ? r : l;
        }
        return null;
    }

}
