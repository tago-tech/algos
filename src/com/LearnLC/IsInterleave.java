package com.LearnLC;



public class IsInterleave {
    public static boolean isInterleave(String s1, String s2, String s3) {

        int M = s1.length(),N = s2.length();
        if (M + N != s3.length()) return false;
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;
        //第一列,对于s3中前M个字符能不能用 s1 组成
        for (int i = 1;i <= M;i++) {
            dp[i][0]= dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        //第一列,对于s3中qianN个字符能不能用 s2 组成
        for (int i = 1;i <= N;i++) {
            dp[0][i] = dp[0][i - 1] && s2.charAt(i - 1) == s3.charAt(i - 1);
        }
        for (int i = 1;i <= M;i++) {
            for (int j = 1;j <= N;j++) {
                //要么用s1组成,要么用s2组成.
                dp[i][j] = s1.charAt(i - 1) == s3.charAt(i + j - 1) && dp[i - 1][j];
                dp[i][j] = dp[i][j] || (s2.charAt(j - 1) == s3.charAt(i + j - 1) && dp[i][j - 1]);
            }
        }
        return dp[M][N];
    }

    //带有空间压缩的方法,将空间使用从 O(M * N)变为 O(Min(M,N))
    public static boolean isInterLeaveWithCompress(String s1,String s2,String s3) {
        if (s1 == null || s2 == null || s3 == null) return false;

        char[] shorter = s1.length() < s2.length() ? s1.toCharArray() : s2.toCharArray();
        char[] longer = s1.length() >= s2.length() ? s1.toCharArray() : s2.toCharArray();
        int M = shorter.length,N = longer.length;
        if (s3.length() != (M + N)) return false;
        boolean[] dp = new boolean[M + 1];
//        System.out.println("Longer = "+longer[0]+" Shorte = "+shorter[0]);
        dp[0] = true;
        //初始化,尝试使用shorter 逐个匹配s3,就是看s3能不能单独由shorter组成shorter[j - 1] == s3.charAt(i + j - 1) && dp[j - 1]
        for (int i = 1;i <= M;i++) {
            dp[i] = dp[i - 1] && shorter[i - 1] == s3.charAt(i - 1);
        }
        for (int i = 1;i <= N;i++) {
            dp[0] = dp[0] && longer[i - 1] == s3.charAt(i - 1);
            for (int j = 1;j <= M;j++) {
                //尝试有由 shorter 构成
                dp[j] = (longer[i - 1] == s3.charAt(i + j - 1) && dp[j]);
                dp[j] = dp[j] || (shorter[j - 1] == s3.charAt(i + j - 1) && dp[j - 1]);
            }
        }

        return dp[M];
    }
    public static void main(String[] args) {
        System.out.println(isInterLeaveWithCompress("aabcc","dbbca","aadbbcbcac"));
    }
}
