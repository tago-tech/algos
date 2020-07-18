package com.LearnLC;
/*
* 将数组划分为三等分,三部分和相同
* 第一部分 [0 ~ i]
* 第二部分 [i + 1 , j - 1]
* 第三部分 [j , len - 1]
*
* 要求 len >= 3
* */
public class partizeArrayto3PWithEqualSum {
    public static boolean canThreePartsEqualSum(int[] A) {
        if(A.length < 3) return false;
        int pLeft,pRight;
        int i = 0,j = 0;
        int[] sumTracked = new int[A.length];
        for (int idx = 0;idx < A.length;idx++){
            if(idx == 0){
                sumTracked[idx] = A[idx];
            }
            else {
                sumTracked[idx] = A[idx] + sumTracked[idx - 1];
            }
        }

        for(i = 0 ;i < A.length - 2;i++){
            System.out.println(i);
            pLeft = sumTracked[i];
            pRight = sumTracked[A.length - 1] - sumTracked[i];
            if (pLeft * 2 == pRight) {
                break;
            }
        }
        for(j = i + 1;j < A.length - 1;j++){
            pLeft = sumTracked[j] - sumTracked[i];
            pRight = sumTracked[A.length - 1] - sumTracked[j];
            if (pLeft == pRight) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        int[] nums = {3,3,6,5,-2,2,5,1,-9,4};
        System.out.println("\n"+canThreePartsEqualSum(nums));
    }
}


