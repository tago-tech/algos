package com.basicalgos;

import java.util.HashMap;
import java.util.Map;

public class StringsProblem {

    /**
     * 判断 s 是 t 的子序列
     * */
    public static boolean isSubsequence(String s, String t) {
        if (s == null || t == null || t.length() < s.length()) return false;
        int i = 0,j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /**
     * 最长的重复子串
     * 动态规划
     * 就是子串的出现不止一次
     * dp[i][j] 表示两个子串分别以 [i]、[j] 结尾
     * dp[i][j] 依赖于 dp[i - 1][j - 1]
     * */
    public static String longestDupSubstring2(String S) {
        if (S == null || S.length() == 0) return "";
        Map<Character,Integer> map = new HashMap<>();
        String s = "";
        for (char c : S.toCharArray()) {
            map.put(c,map.getOrDefault(c,0) + 1);
        }
        for (char c : S.toCharArray()) {
            if (map.get(c) > 1) {
                s += c;
            }
        }
        if (s == "") return "";
        int n = s.length() , max = 0 , maxIdx = -1;
        int[][] dp = new int[n][n];
        for (int i = 0;i < n;i++) {
            for (int j = i;j < n;j++) {
                if (i == j) {
                    dp[i][j] = 0;
                }
                else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) ? (dp[i - 1][j - 1] + 1) : 0;
                }
                if (dp[i][j] > max) {
                    max = dp[i][j];
                    maxIdx = j;
                }
            }
        }
        String target = "";
        while (max-- > 0) {
            target = s.charAt(maxIdx--) + target;
        }
        return target;
    }


}
