package com.LearnLC;

import java.util.HashMap;
import java.util.Map;

public class CoinChange {
    /*
     * 动态规划方法
     * 1. 使用硬币 组成金额 0,全部为 1 种 方法,dp[0][0 ~  Max] = 0
     * 2. dp[i][j] 与 xx 有关
     * 注意越界
     * */
    public static int coinChange(int[] coins, int amount) {
        //dp[i][j] 表示使用 0 ~ j - 1  硬币 组成 钱数目 i 的种类数

        if(amount == 0) return 0;
        if(amount > 0 && coins.length == 0) {
            return -1;
        }
        else {
            return dp(coins,amount);
//            return nextChange(coins,amount,0);
        }
    }
    //递归处理
    public static int nextChange(int[] coins,int rest,int idx){
        if(idx == coins.length){
            return rest == 0 ? 0 : -1;
        }
        int res = -1;
        for(int k = 0; k * coins[idx] <= rest;k++){
            int next = nextChange(coins,rest - k * coins[idx],idx + 1);
            //next 可能是 -1,也有可能是正常值
            if(next != -1){
                res = res == -1 ? next + k : Math.min(res,next + k);
            }
        }
        return res;
    }
    public static int dp(int[] coins,int amount){
        /*
        * rest 表示剩余金额,idx 表示使用的货币坐标
        * dp[rest][idx] 表示当前货币坐标为 idx 凑齐 金额 rest 的方法数.
        * 目的是求 dp[rest][idx],除dp[0][N] = 0 之外,dp[1...amount][N]其余都为 -1
        * dp[rest][idx] 受到 dp[rest - 0 * coins[idx]][idx + 1] + 0,...,dp[rest - k * coins[idx]][idx + 1] + k,直到 k * coins[idx] >= rest 越界;
        * dp[rest - coins[idx]][idx] 受到 dp[rest - 1 * coins[idx]][idx],....,dp[rest - k * coins[idx]][idx],直到 k 越界
        * 所以dp[rest][idx]  = min( dp[rest - coins[idx]][idx] + 1,dp[rest][idx + 1])
        * */
        //初始化dp矩阵
        int[][] dp = new int[amount + 1][coins.length + 1];
        for(int i = 1;i <= amount;i++){
            dp[i][coins.length] = -1;
        }

        for(int i = coins.length - 1;i > -1;i--){
            for(int j = 0;j <= amount;j++){
                dp[j][i] = -1;
                if(dp[j][i + 1] != -1){
                    dp[j][i] = dp[j][i + 1];
                }
                if(j - coins[i] >= 0 && dp[j - coins[i]][i] != -1){
                    if(dp[j][i] == -1){
                        dp[j][i] = dp[j - coins[i]][i] + 1;
                    }
                    else {
                        dp[j][i] = Math.min(dp[j][i],dp[j - coins[i]][i] + 1);
                    }
                }
            }
        }
        return dp[amount][0];
    }
    public static void main(String[] args){
        int[] coins = {3,7,405,436};
//        int[] coins = {1,2,5};
        System.out.println(coinChange(coins,8839));
    }
}
