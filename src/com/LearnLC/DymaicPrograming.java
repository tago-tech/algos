package com.LearnLC;

import java.util.ArrayList;
import java.util.List;

public class DymaicPrograming {
    //石头碰撞 lc 1049
    public static int lastStoneWeightII(int[] stones) {
        if (stones == null || stones.length == 0) return 0;
        return f(stones,stones.length - 1);
    }
    public static int f(int[] stones,int index) {
        for (int i = 0;i < stones.length;i++) System.out.print(stones[i] +",");
        System.out.println();
        if (index == 0) return stones[index];
        int res = Integer.MAX_VALUE;
        for (int i = index - 1;i >= 0;i--) {
            //尝试与每一块石头碰撞
            int temp1 = stones[index];
            int temp2 = stones[i];

            if (stones[i] == stones[index]) {
                stones[i] = 0;
            }
            else {
                stones[i] = Math.abs(stones[i] - stones[index]);
            }
            stones[index] = 0;
            res = Math.min(f(stones,index - 1),res);
            //碰撞之后恢复
            stones[index] = temp1;
            stones[i] = temp2;
        }
        return res;
    }

    public static int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) return 0;
        int strCount = strs.length,c0 = 0,c1 = 0;
        String tempStr;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1;i <= strCount;i++) {
            c0 = 0;
            c1 = 0;
            tempStr = strs[i - 1];
            for (int idx = 0;idx < tempStr.length();idx++) {
                if (tempStr.charAt(idx) == '0') c0++;
                else c1++;
            }
            //使用第 i - 1 个字符
            for (int j = m;j >= c0;j--) {
                for (int k = n;k >= c1;k--) {
                        dp[j][k] = Math.max(dp[j][k],dp[j - c0][k - c1] + 1);

                }
            }
        }
        return dp[m][n];
    }

    //零钱兑换
    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (coins == null || coins.length == 0) return -1;
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1;i < dp.length;i++) dp[i] = -1;
        for (int i = 0;i < coins.length;i++) {
            for (int j = coins[i];j <= amount;j++){
                //默认dp[j] = 上一个dp[j]
                if (dp[j - coins[i]] != -1 && dp[j] != -1) {
                    dp[j] = Math.min(dp[j],dp[j - coins[i]] + 1);
                }
                else if (dp[j] == -1 && dp[j - coins[i]] != -1) {
                    dp[j] = dp[j - coins[i]] + 1;
                }
            }
        }
        return dp[amount];
    }

    public static int change(int amount, int[] coins) {
        if (amount == 0) return 1;
        if (coins == null || coins.length == 0 || amount < 0) return 0;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 0;i < coins.length;i++) {
            for (int j = coins[i];j <= amount;j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }
    public static boolean endwiths(char[] chrs,int endIndex,String str) {
        //判断chrs的[0...endIndex] 是否可以以 str为结尾
        if (str.length() > endIndex + 1) return false;
//        while (endIndex >= 0) {
//            if (chrs[endIndex] == str.charAt())
//        }
        return false;
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        char[] chrArrays = s.toCharArray();
        String subStr;
        String stemp;
        boolean[] gp = new boolean[s.length() + 1];
        gp[0] = true;
        for (int step = 1;step <= s.length();step++) {
            subStr = s.substring(0,step);
            for (int i = 0;i < wordDict.size();i++) {
                stemp = wordDict.get(i);
                if (subStr.endsWith(stemp) && gp[step - stemp.length()]) {
                    gp[step] = true;
                    break;
                }
            }
        }
        return gp[s.length()];
    }

    public static int f1(int[] nums,int target) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0;i < nums.length;i++) {
            for (int j = nums[i];j <= target;j++) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
    public static int f2(int[] nums,int target) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        /*
        * dp[i] 表示凑齐 i 的方法数
        * 1.i >= nums[0]，使用 nums[0] 间接凑齐, dp[i - nums[0]]
        * 2.i >= nums[1]，使用 nums[1] 间接凑齐, dp[i - nums[1]]
        * */
        for (int i = 1;i <= target;i++) {
            for (int j = 0;j < nums.length;j++) {
                if (i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }
    public static void main(String[] args) {
        int[] a = {1,2,3};
        System.out.println(f2(a,4));
    }
}
