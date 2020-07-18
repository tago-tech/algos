package com.LearnLC;

public class MinEditDistance {
    public static int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        int l1 = word1.length(),l2 = word2.length();
        int[][] dp = new int[l1 + 1][l2 + 1];
        //dp[i][j] 表示将 words1[0...i - 1] 转换成 words2[0...j - 1]的最小操作数.
        //dp[0][0 ... l2 - 1],标示不断插入
        for (int idx = 1;idx <= l2;idx++)
            dp[0][idx] = idx;
        //表示不断删除
        for (int idx = 1;idx <= l1;idx++)
            dp[idx][0] = idx;
        dp[0][0] = 0;
        for (int i = 1;i <= l1;i++) {
            for (int j = 1;j <= l2;j++) {
                //求dp[i][j] dp[0..i - 1][0..j - 1]
                //1.dp[0..i - 2][0..j - 1] 删除
                //2.dp[0..i - 1][0..j - 2] 插入
                int del = dp[i - 1][j] + 1;
                int insert = dp[i][j - 1] + 1;
                int replace = 0;
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    replace = dp[i - 1][j - 1];
                else replace = dp[i - 1][j - 1] + 1;
                int cost = Math.min(del,insert);
                cost = Math.min(cost,replace);
                dp[i][j] = cost;
            }
        }
        return dp[l1][l2];
    }
    public static boolean oneEditAway(String first, String second) {
        //插入\删除\替换
        if (first == null || second == null) return false;
        int i = 0,j = 0;
        int del = 1,insert = 1,replace = 1;
        if (Math.abs(first.length() - second.length()) > 1) return false;
        //长度相等时
        if (first.length() == second.length()) {
            while (i < first.length() && j < second.length()) {
                if (first.charAt(i) != second.charAt(j) && replace > 0) {
                    replace--;
                }
                else if (first.charAt(i) != second.charAt(j) && replace <= 0) {
                    return false;
                }
                i++;
                j++;
            }
        }
        else {
            //尝试删除
            i = j = 0;
            String shorter = first.length() > second.length() ? second : first;
            String longer = first.length() > second.length() ? first : second;
            while (i < shorter.length() && j < longer.length()) {
                if(shorter.charAt(i) != longer.charAt(j) && del > 0){
                    del--;
                    i--;
                }
                else if (shorter.charAt(i) != longer.charAt(j) && del <= 0){
                    return false;
                }
                i++;
                j++;
            }
            //尝试插入
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(oneEditAway("pale","ple"));
    }
}
