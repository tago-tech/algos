package com.LearnLC;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.XmlOneway;
import javafx.collections.transformation.SortedList;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
* 最多交易两手
*
* 1.递归写法
* 2.动态规划
* */

public class BestTimeToBSStock3 {
    //最多交易两次,最多持有一份股票
    public static int maxProfit(int[] prices) {
        //正向状态转移
        int N = prices.length;
        if (N == 0) return 0;
        //天数 , 交易数 , 持有状态
        int[][][] dp = new int[N][3][2];
        int max = Integer.MIN_VALUE;

        for (int day = 0;day < N;day++) {
            for (int businessCount = 1;businessCount < 3;businessCount++) {
                if (day == 0) {
                    dp[day][businessCount][0] = 0;
                    dp[day][businessCount][1] = -prices[day];
                    continue;
                }
                dp[day][businessCount][0] = Math.max(dp[day - 1][businessCount][0],
                        dp[day - 1][businessCount][1] + prices[day]);
                // 为什么使用 买入作为 交易数增加,留到以后思考
                dp[day][businessCount][1] = Math.max(dp[day - 1][businessCount][1],
                        dp[day - 1][businessCount - 1][0] - prices[day]);
            }
        }
        for (int k = 0;k < 3;k++) {
            max = Math.max(max,dp[N - 1][k][0]);
        }
        return max;
    }
    //1.递归处理
    public static int stepOnProcess(int[] prices,int day,boolean haveStockOnhand,int businessCount){
        //边界条件
        if ( day >= prices.length || (businessCount >= 2 && !haveStockOnhand)) {
            return 0;
        }
        //有股票在手上
        if (haveStockOnhand) {
            // 卖出 或者 不卖
            return Math.max(prices[day] + stepOnProcess(prices, day + 1,false, businessCount + 1),
                    stepOnProcess(prices,day + 1,true,businessCount));
        }
        else {
            //买入或者不买入
            return Math.max(-prices[day] + stepOnProcess(prices,day + 1,true,businessCount),
                    stepOnProcess(prices,day + 1,false,businessCount));
        }
    }
    //2.通俗递归方法1
    public static int dpFunc1(int[] prices) {
        int N = prices.length;
        //(0 ~ N天) = N + 1 天,(持有或没有)共两种状态,(卖出次数0,1,2)共三种状态
        int[][][] dp = new int[N + 1][2][3];
        for (int day = N - 1;day > -1;day--) {
            for (int haveStockOnHand = 0;haveStockOnHand < 2;haveStockOnHand++) {
                for (int businessCount = 0;businessCount < 3;businessCount++) {
                    if ( businessCount >= 2 && haveStockOnHand < 1) {
                        continue;
                    }
                    if (haveStockOnHand == 1) {
                        //有股票在手上
                        //卖出 或者 不变
                        dp[day][haveStockOnHand][businessCount] = dp[day + 1][1][businessCount];
                        //限制交易次数才能卖出
                        if (businessCount < 2) {
                            dp[day][haveStockOnHand][businessCount] = Math.max(prices[day] + dp[day + 1][0][businessCount + 1],
                                    dp[day + 1][1][businessCount]);
                        }
                    }
                    else {
                        //没有股票在手上
                        //买入或者不变
                        dp[day][haveStockOnHand][businessCount] = Math.max(-prices[day] + dp[day + 1][1][businessCount],
                                dp[day + 1][0][businessCount]);
                    }
                }
            }
        }
        //0天,无股票在手,0次交易
        return dp[0][0][0];
    }
    public static void main(String[] args){
        int[] prices = {};
        System.out.println(maxProfit(prices));
        //{1,2,4,2,5,7,2,4,9,0} 13
        //[3,3,5,0,0,3,1,4] 6
    }
}
