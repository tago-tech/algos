package com.LearnLC;

import java.util.ArrayList;
import java.util.List;

/*
* 8皇后问题
* 同行同列,斜线上
* */
public class NQueens {
    //第一种方案,简单递归,逐个判断合法性,不断递归
    public static int solveNQueens(int n) {
//        List<List<String>> result = new ArrayList<>();
//        int[] rand = new int[n];
//        int res = process(result,rand,0);
        if (n < 1 || n > 32) return -1;
        int allPoss = n == 32 ? -1 : (1 << n) - 1;
        int res = processDigtal(allPoss,0,0,0);
        return res;
    }
    //判断当前位置的合法性
    public static boolean isVaild(int[] rand,int j,int index) {
        for (int i = 0;i < index;i++) {
            //(a,b) \ (i,j) => a-i == b - j
            if (rand[i] == j || Math.abs(rand[i] - j) == Math.abs(i - index)) {
                return false;
            }
        }
        return true;
    }
    //
    public static int process(List<List<String>> result,int[] rand,int index) {
        //最后一个皇后已安置
        if (index == rand.length) {
            List<String> singleSolve = new ArrayList<>();
            //生成一个字符串
            for (int i = 0;i < rand.length;i++) {
                char[] chrs = new char[rand.length];
                for (int j = 0;j < rand.length;j++) {
                    chrs[j] = '.';
                }
                chrs[rand[i]] = 'Q';
                singleSolve.add(new String(chrs));
            }
            result.add(singleSolve);
            return 1;
        }
        int res = 0;
        //循环判断某一列是否可行
        for (int j = 0;j < rand.length;j++) {
            if (isVaild(rand,j,index)) {
                rand[index] = j;
                //处理下一行
                res += process(result,rand,index + 1);
            }
        }
        return res;
    }

    //第二种方案,仍然递归,但使用位运算进行加速
    public static int processDigtal(int allPoss,int posUsed,int leftInfl,int rightInfl) {
        if (posUsed == allPoss) {
            return 1;
        }
        int rs = 0;
        //求出所有的可用位置
        int pos = allPoss & ~(posUsed | leftInfl | rightInfl);
        int mostRightAviabel = 0;
        while (pos > 0) {
            //找到pos中最右边的 bit == 1,有待深入学习
            mostRightAviabel = pos & ( ~pos + 1);
            pos = pos - mostRightAviabel;
            rs += processDigtal(allPoss,(posUsed | mostRightAviabel),(leftInfl | mostRightAviabel) << 1,
                    (rightInfl | mostRightAviabel) >>> 1);
        }
        return rs;
    }

    public static void main(String[] args) {
//        List<List<String>> result = solveNQueens(4);
//        for(int i = 0;i < result.size();i++){
//            System.out.println(result.get(i));
//        }
        System.out.println(solveNQueens(2));
    }
}
