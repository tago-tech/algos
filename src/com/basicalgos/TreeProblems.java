package com.basicalgos;

import com.datastructure.TreeNode;

import java.util.*;

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

    /**
     * 二叉树的锯齿形层序遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        TreeNode current = null , layerEnding = root;

        boolean leftToRight = true;

        Deque<TreeNode> deque = new LinkedList<>();

        deque.addLast(root);

        List<Integer> orderedElements = new ArrayList<>();

        while (!deque.isEmpty()) {

            TreeNode c = leftToRight ? deque.pollLast() : deque.pollFirst();

            if (c == null) continue;

            orderedElements.add(c.val);

            if (leftToRight) {
                deque.addFirst(c.left);
                current = current == null ? c.left : current;
                deque.addFirst(c.right);
                current = current == null ? c.right : current;
            }
            else {
                deque.addLast(c.right);
                current = current == null ? c.right : current;
                deque.addLast(c.left);
                current = current == null ? c.left : current;
            }

            //准备进入下一层
            if (layerEnding == c) {

                result.add(orderedElements);

                orderedElements = new ArrayList<Integer>();

                //更新 layerEnding
                layerEnding = current;

                current = null;

                leftToRight = !leftToRight;
            }

        }
        System.out.println(result);
        return result;
    }

}
