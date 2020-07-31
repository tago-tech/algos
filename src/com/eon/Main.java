package com.eon;

import java.util.Scanner;

//https://www.nowcoder.com/activity/2020cmb/index
public class Main {
    public static int maxVale (int[][] nums) {

        int[] dp = new int[nums.length];
        dp[0] = nums[0][0];
        for (int i = 1;i < nums.length;i++) {

            for (int j = i;j > -1;j--) {
                if (j > 0)
                    dp[j] = Math.max(dp[j],dp[j - 1]);
                dp[j] += nums[i][j];

            }
        }
        int max = 0;
        for (int i = 0;i < nums.length;i++) {
            max = Math.max(max,dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[][] nums = new int[n][n];
        for (int i = 0;i < n;i++) {
            for (int j = 0 ;j <= i;j++) {
                nums[i][j] = scn.nextInt();
            }
        }
        System.out.println(maxVale(nums));

    }
}
