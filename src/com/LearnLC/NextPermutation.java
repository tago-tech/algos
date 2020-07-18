package com.LearnLC;

import java.util.Arrays;

public class NextPermutation {
    public static void nextPermutation(int[] nums) {
        int minMaxIndex = -1 , temp;
        for(int idx = nums.length - 2 ; idx >= 0; idx--){
            if(nums[idx + 1] > nums[idx]){
                minMaxIndex = idx + 1;
                for(int j = nums.length - 1;j > idx;j--){
                    if(nums[j] > nums[idx] && nums[minMaxIndex] > nums[j]) minMaxIndex = j;
                }
                //交换
                temp = nums[idx];
                nums[idx] = nums[minMaxIndex];
                nums[minMaxIndex] = temp;

                Arrays.sort(nums,idx+1,nums.length);
                return;
            }
        }
        Arrays.sort(nums);
    }
    public static void main(String[] args){
        int[] nums = {};
        nextPermutation(nums);
        for(int item : nums){
            System.out.print(item+",");
        }
    }
}
