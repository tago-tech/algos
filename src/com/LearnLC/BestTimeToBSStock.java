package com.LearnLC;

public class BestTimeToBSStock {
    /*
    * best-time-to-buy-and-sell-stock
    * */
    public static int maxProfit(int[] prices) {
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
        int[] prices = {1,2,3,4,5};
        System.out.println(maxProfit(prices));
    }
}
