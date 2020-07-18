package com.LearnLC;

import javax.swing.*;
import java.util.*;

/*
* 二叉树题目合集
* */
class AnnTreeNode{
    TreeNode node;
    int pos,depth;
    AnnTreeNode(TreeNode node,int pos ,int depth) {
        this.node = node;
        this.pos = pos;
        this.depth = depth;
    }
}
public class BinaryTreeCollection {
    //二叉树的右视图
    public static List<Integer> rightSideView(TreeNode root) {

        List<Integer> rs = new ArrayList<>();
        if (root == null) return rs;
        TreeNode last = root,nextLast = null;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur.left != null) {
                q.add(cur.left);
                nextLast = cur.left;
            }
            if (cur.right != null) {
                q.add(cur.right);
                nextLast = cur.right;
            }
            if (cur == last) {
                rs.add(cur.val);
                last = nextLast;
            }
        }
        return rs;
    }

    //二叉树的之字形打印,双端队列，改变传统Queue只能从一端存放就行
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> rs = new ArrayList<>();
        if (root == null) return rs;
        boolean leftToRight = true;
        TreeNode lst = root,nextLst = null,cur = null;
        LinkedList<TreeNode> dq = new LinkedList<>();
        List<Integer> tempList = new ArrayList<>();
        dq.offer(root);
        while (!dq.isEmpty()) {
            //从左向右
            if (leftToRight) {
                cur = dq.pollFirst();
                if (cur.left != null) {
                    dq.offerLast(cur.left);
                    nextLst = nextLst == null ? cur.left : nextLst;

                }
                if (cur.right != null) {
                    dq.offerLast(cur.right);
                    nextLst = nextLst == null ? cur.right : nextLst;
                }
            }
            else {
                //反方向
                cur = dq.pollLast();
                if (cur.right != null) {
                    nextLst = nextLst == null ? cur.right : nextLst;
                    dq.offerFirst(cur.right);
                }
                if (cur.left != null) {
                    nextLst = nextLst == null ? cur.left : nextLst;
                    dq.offerFirst(cur.left);
                }
            }
            tempList.add(cur.val);
            if (cur == lst) {
                lst = nextLst;
                nextLst = null;
                rs.add(tempList);
                tempList = new ArrayList<>();
                leftToRight = !leftToRight;
            }
        }
        return rs;
    }

    //求二叉树的层值平均值的数组
    public static List<Double> averageOfLevels(TreeNode root) {
        List<Double> rs = new ArrayList<>();
        TreeNode last = root,nextLast = null;
        //当前行数字的个数
        int count = 0;
        //valSum代表当前求到的平均值,不用代表总和是为了防止溢出
        double valSum = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            //为了防止溢出,valSum这里记录平均值

            valSum += (cur.val - valSum) / (count + 1);

            count++;
//            (cur.val - valSum) / count
//            valSum += cur.val;
//            count++;
            if (cur.left != null) {
                q.add(cur.left);
                nextLast = cur.left;
            }
            if (cur.right != null) {
                q.add(cur.right);
                nextLast = cur.right;
            }
            if (cur == last) {
                rs.add(valSum);
                valSum = 0;
                count = 0;
                last = nextLast;
            }
        }
        return rs;
    }
    //二叉树的最大宽度,第一种方案宽度优先遍历
    public static int widthOfBinaryTree(TreeNode root) {
        int maxWidth = 0;
        if (root == null) return 0;
        Queue<AnnTreeNode> q = new LinkedList<>();
        int cureDepth = 0;
        int start = -1,end = -1;
        q.add(new AnnTreeNode(root,0,0));
        while (!q.isEmpty()) {
            AnnTreeNode cur = q.poll();
            //下一行的起点,start
            if (cur.node != null) {
                q.add(new AnnTreeNode(cur.node.left,cur.pos * 2,cur.depth + 1));
                q.add(new AnnTreeNode(cur.node.right,cur.pos * 2 + 1,cur.depth  + 1));
                if (cureDepth != cur.depth) {
                    start = cur.pos;
                    cureDepth = cur.depth;
                }
                maxWidth = Math.max(maxWidth,cur.pos - start + 1);
            }
        }
        return maxWidth;
    }
    //二叉树的最大宽度,中间的null节点也记录在内
    public static int widthOfBinTreeDFS(TreeNode root) {
        List<Integer> lefts = new ArrayList<>();
        return dfs(root,0,0,lefts);
    }
    /*
    * 宽度优先搜索,保持一个数组left记录当前层最left的位置,也就是left[level]最小的地方.
    * 当然如果是null直接返回0,如果不是就返回当前root.pos - left,然后计算左子树\右子树上最大值,然后返回三者最大值
    * left数组的索引表示层数,表示当前层的最小的 == 最left的;
    * 深度遍历时候首先遍历左子树
    * 这种方法比广度优先遍历 BFS要方便
    * */
    public static int dfs(TreeNode root,int pos,int level,List<Integer> lefts) {
        if(root == null) return 0;
        if (level >= lefts.size()) {
            lefts.add(Integer.MAX_VALUE);
        }
        lefts.set(level,Math.min(lefts.get(level),pos));
        int widthRoot = pos - lefts.get(level) + 1;
        int leftWidth = dfs(root.left,pos * 2,level + 1,lefts);
        int rightWidth = dfs(root.right,pos * 2 + 1,level + 1,lefts);
        return Math.max(leftWidth,Math.max(rightWidth,widthRoot));
    }
    //最近公共祖先
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode leftFinder = lowestCommonAncestor(root.left,p,q);
        TreeNode rightFinder = lowestCommonAncestor(root.right,p,q);
        if (leftFinder != null && rightFinder != null) return root;
        else return leftFinder == null ? rightFinder : leftFinder;
    }


    /*
    * 二叉树的序列化与反序列化
    * 第一种方案:先序序列化,首先将字符串spilt，然后利用Queue结构逐个弹出摊入
    * 第二种：层次遍历序列化
    * */
    // Encodes a tree to a single string.

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) return null;
        String[] values = data.split("!");
        Queue<String> q = new LinkedList<String>();
        for (int i =0;i < values.length;i++) {
            q.offer(values[i]);
        }
//        return deserializePreStes(q);
        return deserializeNonLayer(q);
    }
    public static String serialize(TreeNode root) {
        if (root == null) return "N!";
        String rs = root.val + "!";
        rs += serialize(root.left);
        rs += serialize(root.right);
        return rs;
    }
    public static TreeNode deserializePreStes(Queue<String> q) {
        if (q.isEmpty()) return null;
        String sTemp = q.poll();
        if (sTemp.equals("N")) return null;
        TreeNode root = new TreeNode(Integer.parseInt(sTemp));
        root.left = deserializePreStes(q);
        root.right = deserializePreStes(q);
        return root;
    }
    //非递归的层次遍历序列化
    public static String serializeNonLayer(TreeNode root) {
        StringBuilder rs = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode temp = q.poll();
            rs.append(temp == null ? "N!" : temp.val + "!");
            if (temp != null) {
                q.offer(temp.left);
                q.offer(temp.right);
            }
        }
        return rs.toString();
    }
    public static TreeNode deserializeNonLayer(Queue<String> queue) {
        String rootStr = queue.poll();
        if (rootStr.equals("N")) return null;
        TreeNode root = new TreeNode(Integer.parseInt(rootStr));
        Queue<TreeNode> qs = new LinkedList<>();
        qs.offer(root);
        while (!qs.isEmpty()) {
            TreeNode temp = qs.poll();
            String leftStr = queue.poll();
            TreeNode leftChild = leftStr.equals("N") ? null : new TreeNode(Integer.parseInt(leftStr));
            if (leftChild != null) qs.offer(leftChild);

            String rightStr = queue.poll();
            TreeNode rightChild = rightStr.equals("N") ? null : new TreeNode(Integer.parseInt(rightStr));
            if(rightChild != null) qs.offer(rightChild);

            temp.left = leftChild;
            temp.right = rightChild;
        }
        return root;
    }

    //二叉搜索树中第K小的元素
    public static int kthSmallest(TreeNode root, int k) {
        //第一种方法用二叉树的中序遍li

        //第二种递归求法
        int leftsNum = countNodeNum(root.left);
        if (leftsNum >= k) {
            //
            return kthSmallest(root.left,k);
        }
        else if (k == leftsNum + 1) {
            return root.val;
        }
        else {
            return kthSmallest(root.right,k - leftsNum - 1);
        }
    }
    //查找二叉树中节点个数
    public static int countNodeNum(TreeNode root) {
        if (root == null) return 0;
        return countNodeNum(root.left) + countNodeNum(root.right) + 1;
    }
    //二叉树的先序遍历 非递归
    public static void preOrderUnRecur(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode t = stack.pop();
            //访问 t 节点
            System.out.println(t.val);
            if (t.right != null) stack.push(t.right);
            if (t.left != null) stack.push(t.left);
        }
        return;
    }

    //二叉树的中序遍历 非递归
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                if (cur.left != null) {
                    cur = cur.left;
                }
                else {
                    //这一步代表访问右子树的时候，根节点已经被弹出了，不会被重复访问
                    cur = stack.pop();
                    rs.add(cur.val);
                    cur = cur.right;
                }
            }
            else {
                cur = stack.pop();
                rs.add(cur.val);
                cur = cur.right;
            }
        }
        return rs;
    }

    //二叉树的后序遍历 非递归
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        if (root == null) return rs;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode h = root,c = null;
        stack.push(root);
        while (!stack.isEmpty()) {
            c = stack.peek();
            //判断第一次访问的条件是没有访问过左右子树。
            //这里的h != c.right，初看没有考虑c.right为空的情况，但是
            //初始时 h == root，肯定不会满足，遍历过程中 h 肯定非空，即使c.right 为空也无所谓
            if (c.left != null && h != c.left && h != c.right) {
                //第一次访问该节点，由于是后序遍历 所以先访问非空的左子树
                stack.push(c.left);
            }
            else if (c.right != null && h != c.right) {
                //第二次访问该节点，要么已经访问过左子树，要么左子树为空
                stack.push(c.right);
            }
            else {
                //从左右子树上退回到当前节点，该弹出并打印了
                h = stack.pop();
                rs.add(h.val);
            }
        }
        return rs;
    }

    //构造二叉树
    //从前序和1中序中构造二叉树
    public static TreeNode buildTree(int[] preorder, int[] inorder) {

        return buildNodePreWithIn(preorder,inorder,0,preorder.length - 1,0,inorder.length - 1);
    }
    public static TreeNode buildNodePreWithIn(int[] preorder,int[] inorder,int preStart,int preEnd,int inStart,int inEnd) {
        if (preEnd < preStart || inStart > inEnd) return null;
        //新建节点
        TreeNode root = new TreeNode(preorder[preStart]);
        int inIndex = indexof(inorder,preorder[preStart],inStart,inEnd);
        //左子树节点长度是 inIndex - inStart , 右子树长度是 inEnd - inIndex
        root.left = buildNodePreWithIn(preorder,inorder,preStart + 1,preEnd - (inEnd - inIndex),inStart,inIndex - 1);
        root.right = buildNodePreWithIn(preorder,inorder,preStart + 1 + (inIndex - inStart),preEnd,inIndex + 1,inEnd);
        System.out.println("Node "+root.val);
        return root;
    }
    public static int indexof(int[] nums,int target,int start,int end) {
        for (int i = start;i <= end;i++) {
            if (nums[i] == target) return i;
        }
        return -1;
    }
    //从后序和中序中构造二叉树
    public static TreeNode buildTreeInWithPost(int[] inorder, int[] postorder) {
        return inWithPostBuildTree(inorder,postorder,0,inorder.length - 1,0,postorder.length - 1);
    }
    public static TreeNode inWithPostBuildTree(int[] inorder, int[] postorder,int inStart,int inEnd,int postStart,int postEnd) {
        if (inStart > inEnd || postStart > postEnd) return null;
        TreeNode root = new TreeNode(postorder[postEnd]);
        int rootInIndex = indexof(inorder,postorder[postEnd],inStart,inEnd);
        root.left = inWithPostBuildTree(inorder,postorder,inStart,rootInIndex - 1,postStart,postStart + (rootInIndex - inStart) - 1);
        root.right = inWithPostBuildTree(inorder,postorder,rootInIndex + 1,inEnd,postEnd - (inEnd - rootInIndex),postEnd - 1);
        return root;
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
//        root.left.right = new TreeNode(4);
//        root.right.right = new TreeNode(5);

        System.out.println(postorderTraversal(root));

        int[] in = {9,3,15,20,7};
        int[] post = {9,15,7,20,3};
        root = buildTreeInWithPost(in,post);
        System.out.println(postorderTraversal(root));

    }
}
