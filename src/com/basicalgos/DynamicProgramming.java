package com.basicalgos;

public class DynamicProgramming {

    //不同种类二叉树
    public static int numTrees(int n) {
        if (n < 2) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2;i <= n;i++) {
            for (int j = 0;j < i;j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    //找出字符串 s 中最长的回文串
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";
        int n = s.length();
        //改写，使其 O(1) 空间复杂度
        //boolean[][] dp = new boolean[s.length()][s.length()];

        int start =   0 ,end = 0,maxLen = Integer.MIN_VALUE;
        boolean dp = false;
        int[][] moveTensor = {{0,1},{1,0}};
        int xStart = 0,yStart = 0,idx = 0;
        while (xStart < n && yStart < n) {
            int x = xStart,y = yStart;
            dp = false;
            while (x > -1 && y < n) {
                if (x == y) {
                    dp = true;
                }
                else if (x + 1 == y) {
                    dp = s.charAt(x) == s.charAt(y);
                }
                else {
                    dp = s.charAt(x) == s.charAt(y) & dp;
                }
                if (dp && y - x + 1 > maxLen) {
                    start = x;
                    end = y;
                    maxLen = y - x + 1;
                }
                x--;
                y++;
            }
            xStart += moveTensor[idx][0];
            yStart += moveTensor[idx][1];
            idx = (idx + 1) % moveTensor.length;
        }

        return s.substring(start,end + 1);
    }
}
