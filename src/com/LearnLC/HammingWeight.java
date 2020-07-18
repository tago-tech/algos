package com.LearnLC;

public class HammingWeight {
    //通常需要移位 32 次
    public static int hammingWeight(int n) {
        int res = 0;
        while(n != 0){
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }
    //func1: 循环次数只与 1 的个数相关,即每次消除 最右边 1
    public static int hammingWeightS1(int n){
        int res = 0;
        while(n != 0){
            //消除最右边1
            n &= (n - 1);
            res++;
        }
        return res;
    }
    //func2: 循环次数只与 1 的个数相关,即每次消除 最右边 1
    public static int hammingWeightS2(int n){
        /*
        * n         = 01000100
        * ~n        = 10111011
        * ~n + 1    = 10111100
        * n&(~n + 1)= 00000100
        * -         = 01000000
        * */
        int res = 0;
        while(n != 0){
            n -= n & (~n + 1);
            res++;
        }
        return res;
    }
    public static void main(String[] args){
        System.out.println(hammingWeightS2(9));
    }
}
