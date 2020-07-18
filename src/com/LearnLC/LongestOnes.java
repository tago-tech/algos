package com.LearnLC;
/*
* 示例 1：
输入：A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
输出：6
解释：
[1,1,1,0,0,1,1,1,1,1,1]
粗体数字从 0 翻转到 1，最长的子数组长度为 6。
示例 2：
输入：A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
输出：10
解释：
[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
粗体数字从 0 翻转到 1，最长的子数组长度为 10。
* */
public class LongestOnes {
    //窗口法
    public static int longestOnes(int[] A,int K){
        int flowwer,poinner;
        flowwer = poinner = 0;
        int maxLength = 0;
        //终止条件 前驱 到达末尾
        while( poinner < A.length ){
            //优先移动前驱
            if( A[poinner] == 1 ) poinner++;
            else if( K > 0 ){
                K--;
                poinner++;
            }
            else{
                //前驱无法移动,移动后驱
                // K == 0 && A[poinner] == 0
                if( A[flowwer] == 0 ) K++;
                flowwer++;
            }
            maxLength = maxLength > (poinner - flowwer) ? maxLength : poinner - flowwer;
        }
        return maxLength;
    }
    public static void main(String[] args){
        int[] A = {0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1};
        int K = 3;
        System.out.println(longestOnes(A,K));
    }
}
