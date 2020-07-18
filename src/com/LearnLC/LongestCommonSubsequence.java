package com.LearnLC;

public class LongestCommonSubsequence {
    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0) return 0;
        int[][] dp = new int[text1.length()][text2.length()];
        dp[0][0] = text1.charAt(0) == text2.charAt(0) ? 1 : 0;
        int last = dp[0][0];
        for (int i = 1;i < dp.length;i++) {
            if (text2.charAt(0) == text1.charAt(i)) last = 1;
            dp[i][0] = last;
        }
        last = dp[0][0];
        for (int i = 1;i < dp[0].length;i++) {
            if (text2.charAt(i) == text1.charAt(0)) last = 1;
            dp[0][i] = last;
        }

        for (int i = 1;i < text1.length();i++) {
            for (int j = 1;j < text2.length();j++) {
                dp[i][j] = dp[i - 1][j - 1];
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i - 1][j],dp[i][j]);
                    dp[i][j] = Math.max(dp[i][j - 1],dp[i][j]);
                }
            }
        }
        return dp[text1.length() - 1][text2.length() - 1];
    }
    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("",""));
    }
}
