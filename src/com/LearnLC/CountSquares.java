package com.LearnLC;

import java.util.Arrays;

/*
* 动态规划方法
* (i,j) 视为 为 当前点为右下角的 方形的最大边长  == 以当前点为右下角的 各尺寸方形的数目
* (i,j) == 1时,受限于邻居节点的影响.
* */
public class CountSquares {
    public static int countSquares(int[][] matrix) {
        int count = 0;
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        //初始化为0
        for(int i = 0;i < dp.length;i++) Arrays.fill(dp[i],0);
        for (int i = 0;i < matrix.length;i++){
            for (int j = 0;j < matrix[i].length;j++){
                if(matrix[i][j] == 1){
                    dp[i + 1][j + 1] = Math.min(dp[i][j + 1],Math.min(dp[i + 1][j],dp[i][j])) + 1;
                    count += dp[i + 1][j + 1];
                }
            }
        }
        return count;
    }
    public static void main(String[] args){
        int[][] matrix = {
                {1,0,1},
                {1,1,0},
                {1,1,0}
        };
        System.out.println(countSquares(matrix));
    }
}
