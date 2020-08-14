package com.basicalgos;

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
}
