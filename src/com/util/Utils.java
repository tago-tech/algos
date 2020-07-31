package com.util;

import com.company.TreeNode;

import javax.swing.plaf.basic.BasicListUI;
import java.util.*;

public class Utils {
    /**
     * 数学
     * 1.阶乘
     * 2.最大公约数
     * 3.最小公倍数
     * 4.连乘
     * 5.阶乘中,0的个数
     * */
    //1.long型连乘
    public static long longMuls(long left,long right) {
        long rex = 1;
        while (left <= right) {
            rex *= left;
            left++;
        }
        return rex;
    }
    //2.int型连乘
    public static int intMuls(int left,int right) {
        int rex = 1;
        while (left <= right) {
            rex *= left;
            left++;
        }
        return rex;
    }
    //2.最大公约数
    public static int gcd(int a,int b) {
        int res = 0;
        while (b != 0) {
            res = a % b;
            a = b;
            b = res;
        }
        return a;
    }
    //3.最小公倍数
    public static int lsm(int a,int b) {
        int sum = a * b;
        int res = 0;
        while (b != 0) {
            res = a % b;
            a = b;
            b = res;
        }
        return sum / a;
    }
    //4.组合数
    //https://blog.csdn.net/haiyoushui123456/article/details/84338494
    //4.排列数

    //5.求出int型变量二进制中几位非0;
    public static int NJC(int n) {
        if (n == 0) return 0;
        int sum = 1;
        for (int i = 2;i <= n;i++) {
            sum *= i;
        }
        return sum;
    }


    // 阶乘结果中 0 的个数
    public static int trailingZeroes(int n) {
        int rx = 0;
        //逐个计算，n 中含有一个5 个数，两个 5 个数.3 个 5 个数
        while (n >= 5) {
            rx += n / 5;
            n /= 5;
        }
        return rx;
    }
    /**数组或矩阵
     * 1. 1D\2D 数组打印
     * 2.数组翻转
     * 3.数组全排列
     * 4.数组逆序对统计
     * 5.数组中最大值,最小值
     * */

    //打印二维整形矩阵
    public static void print(int[][] mars) {
        System.out.println();
        int row = mars.length,col = mars[0].length;
        for (int i = 0;i < row;i++) {
            for (int j = 0;j < col;j++) {
                System.out.print(" ," + mars[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    //打印字符串数组
    public static void print (String[] array) {
        for (int i = 0;i < array.length;i++) {
            System.out.print(" , [" + array[i]+"]");
        }
        System.out.println();
    }
    //打印二维字符矩阵
    public static void print(char[][] mars) {
        System.out.println();
        int row = mars.length,col = mars[0].length;
        for (int i = 0;i < row;i++) {
            for (int j = 0;j < col;j++) {
                System.out.print(" ," + mars[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    //打印一维整形矩阵
    public static void print(int[] array) {
        for (int i = 0;i < array.length;i++) {
            System.out.print(" , " + array[i]);
        }
        System.out.println();
    }
    //打印一维字符数组
    public static void print(char[] array) {
        for (int i = 0;i < array.length;i++) {
            System.out.print(" , " + array[i]);
        }
        System.out.println();
    }
    public static void print(boolean[] array) {
        for (int i = 0;i < array.length;i++) {
            System.out.print(" , " + array[i]);
        }
        System.out.println();
    }
    //数组中最大值,最小值
    public static int arrayMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            max = Math.max(max,num);
        }
        return max;
    }
    public static int arrayMin(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int num : array) {
            min = Math.min(min,num);
        }
        return min;
    }

    public void swapNums(int[] nums,int left,int right) {
        int temp;
        temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
    public void reverseNums(int[] nums , int left , int right) {
        int temp;
        while (left < right) {
            //swap left , right
            temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
    //寻找下一个排列 , 若没有则返回最小的排列
    public void nextPermutation(int[] nums) {
        int n = nums.length , idx = n - 1 , pc;
        //123 , 321
        //首先找到第一个逆序的
        while (idx > 0 && nums[idx - 1] >= nums[idx]) {
            idx--;
        }
        //此时序列已经最大
        if (idx == 0) {
            reverseNums(nums,0,n - 1);
            Utils.print(nums);
            //System.out.println("已经最大");
            return;
        }
        //然后找到最小的进行交换
        pc = idx - 1;
        int minIndex = idx , min = nums[idx];
        for (int i = idx;i < n;i++) {
            //这里应该用等号，如果出现重复值，尽量让值靠后
            if (nums[i] > nums[pc] && nums[i] <= min) {
                min = nums[i];
                minIndex = i;
            }
        }
        //swap
        swapNums(nums,pc,minIndex);
        //然后将后半段逆序
        reverseNums(nums,pc + 1,n - 1);
        //Utils.print1DArray(nums);
    }

    /**链表
     * 1.链表计数
     * 2.链表翻转
     * 3.打印链表
     * 4.根据数组构建链表
     * */
    //1.countList
    public static int countList (ListNode head) {
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        return count;
    }
    //2.链表翻转 【left -> right】
    public static void reverseList (ListNode head,int left,int right) {

    }
    //3.打印链表
    public static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " , ");
            head = head.next;
        }
        System.out.println();
    }
    //4.根据数组构建链表
    public static ListNode buildList (int[] nums) {
        if (nums == null || nums.length == 0) return null;
        int idx = 1;
        ListNode head = new ListNode(nums[0]) , cur = head;

        while (idx < nums.length) {
            cur.next = new ListNode(nums[idx]);
            cur = cur.next;
        }
        return head;
    }
    /**二叉树
     * 1.二叉树遍历 :二叉树的 (先序，中序，后序 ，层次) 以及 非递归形式的遍历
     * 2.二叉树的创建 : a)先序 + 中序 b) 中序加后序
     * 3.二叉树节点个数
     * 4.二叉树高度
     * 5.二叉树最近公共祖先
     * **/
    //打印二叉树 ， 先序，中序，后序 (递归 / 非递归)
    public static void preOrdBinTree(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrdBinTree(root.left);
        preOrdBinTree(root.right);
    }
    public static void inOrdBinTree(TreeNode root) {
        if (root == null) return;
        inOrdBinTree(root.left);
        System.out.print(root.val + " ");
        inOrdBinTree(root.right);
    }
    public static void postOrdBinTree(TreeNode root) {
        if (root == null) return;
        postOrdBinTree(root.left);
        postOrdBinTree(root.right);
        System.out.print(root.val + " ");
    }



    //根据先序 中序生成二叉树
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        int length = preorder.length;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int inorderIndex = 0;
        for (int i = 1; i < length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }
    //根据后序中序生成二叉树

    //二叉树高度
    public int depthOfTree(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left_height = depthOfTree(root.left);
            int right_height = depthOfTree(root.right);
            return java.lang.Math.max(left_height, right_height) + 1;
        }
    }

    //5.二叉树最近公共祖先
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

    /** 字符串
     *  1. 字符串反装
     *  2.字符串正则匹配
     *  3. 字符串正常匹配 (KMP)
     * */
    //字符数组翻转
    public static void  revsChrs(char[] chrs,int left,int right) {
        char temp;
        while (left < right) {
            temp = chrs[left];
            chrs[left] = chrs[right];
            chrs[right] = temp;
            right--;
            left++;
        }
    }
    //2.字符串正则匹配
    public static boolean strRegMatch(String s, String p) {
        if (s == null || p == null) return false;
        //动态规划写法
        int n = s.length(),m = p.length();
        char[] chrs = s.toCharArray();
        char[] chrp = p.toCharArray();
        boolean[][] dp = new boolean[n + 1][m + 1];
        /**
         * dp[i][j] 表示 s[i...n] 与 p[j...m]的匹配程度
         * */
        dp[n][m] = true;
        //表示本串已经结束，而模式串未结束
        for (int i = m - 1;i > -1;i--) {
            if (chrp[i] != '*') break;
            dp[n][i] = true;
        }
        for (int i = n - 1;i > -1;i--) {
            for (int j = m - 1;j > -1;j--) {
                if (chrp[j] != '*') {
                    dp[i][j] = (chrs[i] == chrp[j] || chrp[j] == '?') && dp[i + 1][j + 1];
                }
                else {
                    int step = i;
                    while (step <= n && !dp[i][j]) {
                        dp[i][j] |= dp[step][j + 1];
                        step++;
                    }
                }
            }
        }
        return dp[0][0];
    }

    //字符数组转成字符串
    public static String chrsToString(char[] array) {
        String str = "";
        for (char c : array) {
            str += c;
        }
        return str;
    }

    //字符串循环右移
    public static String LeftRotateString(String str,int n) {
        if (str == null || str.length() == 0) return str;
        char[] chrs = str.toCharArray();

        n = n % chrs.length;
        revsChrs(chrs,0,n - 1);
        revsChrs(chrs,n,chrs.length - 1);
        revsChrs(chrs,0,chrs.length - 1);

        return chrsToString(chrs);
    }
    //替换k次后重复字符最长的子串
    public static int characterReplacement(String s, int k) {
        int longestAfterK = 0 , most = 0 , left = 0, right = -1;
        int[] occurCounts = new int[26];
        char[] chrs = s.toCharArray();
        while (right < chrs.length - 1) {
            //right 计数
            right++;
            occurCounts[chrs[right] - 'A']++;
            most = Math.max(most,occurCounts[chrs[right] - 'A']);
            //字串长度 - k > 最多的重复字符长度
            if (right - left + 1 - k > most) {
                occurCounts[chrs[left] - 'A']--;
                left++;
            }
            longestAfterK = Math.max(right - left + 1,longestAfterK);
        }
        return longestAfterK;
    }

}
