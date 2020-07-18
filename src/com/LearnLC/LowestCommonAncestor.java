package com.LearnLC;

//二叉树中最近的公共祖先

public class LowestCommonAncestor {
    //普通二叉树,题目要求是二叉搜索树.
    //时间复杂度o(n),空间复杂度o(n)
    /*
    * 还有另一种方法,标记一个全局变量 v, 搜索一个节点的过程中, 搜索当前值 == 其中一个节点 \ 搜索左子树 \ 搜索右子树,
    * 如果三个之中出现任意两个 true 则代表都找到了
    * 否则就将ture返回给上一层.
    * 但是上面这种方法显然没有下面这种方便.
    * */
    public TreeNode normalBinTreeSearchCommonFather(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        //在左子树上搜索
        TreeNode left = normalBinTreeSearchCommonFather(root.left,p,q);
        //在右子树上搜索
        TreeNode right = normalBinTreeSearchCommonFather(root.right,p,q);
        // 两树上都搜索到了
        if (left != null && right != null)
            return root;
        //其中一个为空,两种情况: 1.要么的确只搜索到了一个;2.要么都在一颗子树上.
        return left == null ? right : left;
    }
    //二叉搜索树的特性, left < root < right
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        //在左子树上搜索
        TreeNode left = null;
        if (p.val < root.val || q.val < root.val)
                left = lowestCommonAncestor(root.left,p,q);
        //在右子树上搜索
        TreeNode right = null;
        if (p.val > root.val || q.val > root.val)
                right = lowestCommonAncestor(root.right,p,q);
        // 两树上都搜索到了
        if (left != null && right != null)
            return root;
        //其中一个为空,两种情况: 1.要么的确只搜索到了一个;2.要么都在一颗子树上.
        return left == null ? right : left;
    }
}
