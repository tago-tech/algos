package com.LearnLC;

import java.util.*;
public class Main {
    public static int f(int[] works,int[] execs,int n) {
        // 0 休息 1 工作 2 锻炼
        int[] dp = new int[3];
        int a , b ,c;
        for (int i = 0;i < n;i++) {
            a = dp[0];
            b = dp[1];
            c = dp[2];
            //今天选择休息
            dp[0] = (1 + Math.min(a,Math.min(b,c)));
            //今天选择 工作
            if (works[i] == 1) {
                dp[1] = Math.min(a,c);
            }
            else {
                dp[1] = Integer.MAX_VALUE;
            }
            if (execs[i] == 1) {
                dp[2] = Math.min(a,b);
            }
            else {
                dp[2] = Integer.MAX_VALUE;
            }
        }
        int min = Integer.MAX_VALUE;

        min = Math.min(min,dp[0]);
        min = Math.min(min,dp[1]);
        min = Math.min(min,dp[2]);
        return min;
    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] works = new int[n];
        int[] exec = new int[n];
        for (int i = 0;i < n;i++) works[i] = scn.nextInt();
        for (int i = 0;i < n;i++) exec[i] = scn.nextInt();
        System.out.println(f(works,exec,n));

    }
}