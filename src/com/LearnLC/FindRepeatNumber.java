package com.LearnLC;

public class FindRepeatNumber {
    public int findRepeatNumber(int[] nums) {
        int temp;
        //每个元素最多交换一次
        for(int i = 0;i < nums.length;i++){
            while(nums[i] != i){
                if(nums[nums[i]] == nums[i]) return nums[i];
                temp = nums[i];
                nums[i] = nums[nums[i]];
                nums[temp] = temp;
            }
        }
        return -1;
    }
    public static void main(String[] args) {

    }
}
