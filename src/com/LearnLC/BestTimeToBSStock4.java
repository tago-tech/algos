package com.LearnLC;

//限制交易次数为 K 次
/*
* 0 =< K < INF
* */
public class BestTimeToBSStock4 {
    public static int maxProfit(int k, int[] prices) {
        int N = prices.length;
        if (k == 0 || N == 0) return 0;
        //当允许的交易次数超出最大可能的交易次数时,就变成随意交易计算了.否则dp矩阵会占用太多内存.
        if (k >= N /2) return maxProfitINF(prices);
        //天数,交易次数,持有状态(0 or 1)
        int[][][] dp = new int[N + 1][k + 1][2];
        for (int day = N - 1;day > -1;day--){
            for (int business = 0;business < k + 1;business++){
                for (int haveStock = 0;haveStock < 2;haveStock++) {
                    if (business >= k && haveStock == 0) {
                        //dp[day][business][haveStock] = 0;
                        continue;
                    }
                    if (haveStock == 1) {
                        //股票在手
                        dp[day][business][haveStock] = dp[day + 1][business][haveStock];
                        //限制交易次数
                        if (business < k) {
                            //卖出 或者 继续持有
                            dp[day][business][haveStock] = Math.max(dp[day + 1][business + 1][0] + prices[day],
                                    dp[day + 1][business][haveStock]);
                        }
                    }
                    else {
                        //没有股票
                        dp[day][business][haveStock] = Math.max(dp[day + 1][business][haveStock],
                                dp[day + 1][business][1] - prices[day]);
                    }
                }
            }
        }
        return dp[0][0][0];
    }
    public static int maxProfitINF(int[] prices) {
        boolean haveStockOnHand = false;
        int earn = 0,money = -1;
        for(int i = 0;i < prices.length;i++){
            if(haveStockOnHand){
                //有股票在手上 想办法卖出
                if((i == prices.length - 1 || prices[i] > prices[i + 1]) && prices[i] > money){
                    earn += prices[i] - money;
                    haveStockOnHand = false;
                }
            }
            else {
                //没有股票在手上想办法买入
                //是否有升值空间
                if (i < prices.length - 1 && prices[i] < prices[i + 1]){
                    money = prices[i];
                    haveStockOnHand = true;
                }
            }
        }
        return earn;
    }
    public static void main(String[] args){
        int[] prices = {7,6,4,3,1};
        System.out.println(maxProfit(1,prices));
    }
}
