package com.LearnLC;

/*
* 简单题:练手
* */
public class RemoveElement {
    public static void showArray(int[] nums){
        System.out.print("Array:[");
        for(int i : nums) System.out.print(" "+i+",");
        System.out.print("]\n");
    }
    public static int removeElement(int[] nums, int val) {
        int flowwer,poinner;
        flowwer = 0;
        for(poinner = 0 ; poinner < nums.length ; poinner++){
            if(nums[poinner] != val){
                nums[flowwer] = nums[poinner];
                flowwer++;
            }
        }
        return flowwer;
    }
    public static void main(String[] args){
//        int[] nums = {3,2,2,3};
        int[] nums = {0,1,2,2,3,0,4,2};

        int val = 2;
        showArray(nums);
        System.out.println("Remove:"+removeElement(nums,val));
        showArray(nums);
    }
}
