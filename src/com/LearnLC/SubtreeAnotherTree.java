package com.LearnLC;

import java.util.LinkedList;
import java.util.Queue;

public class SubtreeAnotherTree {
    public static boolean simial(TreeNode t1,TreeNode t2){
        if (t1 == null && t2 == null) return true;
        if(t1 != null && t2 != null){
            if (t1.val == t2.val) {
                return simial(t1.right,t2.right) && simial(t1.left,t2.left);
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static boolean isSubtree(TreeNode s, TreeNode t) {
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode temp;
        q.add(s);
        while(!q.isEmpty()) {
            temp = q.poll();
            if (simial(temp,t)) return true;
            if (temp.left != null) q.add(temp.left);
            if (temp.right != null) q.add(temp.right);
        }
        return false;
    }

    public static String serializeTree(TreeNode root) {
        if (root == null) return "#!";
        String res = root.val +"!";
        res += serializeTree(root.left);
        res += serializeTree(root.right);
        return res;
    }
    public static int[] calNext(String s) {
        if(s.length() == 0) return null;
        int[] next = new int[s.length()];
        next[0] = -1;
        if (s.length() == 1) return next;
        next[1] = 0;
        int idx = 2,cn = 0;
        while(idx < s.length()) {
            if (s.charAt(idx - 1) == s.charAt(cn)) {
                next[idx++] = ++cn;
            }
            else if (cn > 0) {
                cn = next[cn];
            }
            else {
                next[idx++] = 0;
            }
        }
        return next;
    }
    public static int kmp(String p,String q) {
        if (p.length() == 0 || q.length() == 0 || p.length() < q.length()) return -1;
        int[] next = calNext(q);
        char[] pChrs = p.toCharArray();
        char[] qChrs = q.toCharArray();
        int i = 0,j = 0;
        while (i < pChrs.length && j < qChrs.length) {
            if (pChrs[i] == qChrs[j]) {
                i++;
                j++;
            }
            else if (j > 0) {
                j = next[j];
            }
            else {
                i++;
            }
        }
        if (j == qChrs.length) {
            return i - j;
        }
        else
            return -1;
    }
    public static boolean simialKMP(TreeNode p, TreeNode q) {

        String pStr = serializeTree(p);
        String qStr = serializeTree(q);

        return kmp(pStr,qStr) == 0;
    }
    public static void main(String[] args){

    }
}
