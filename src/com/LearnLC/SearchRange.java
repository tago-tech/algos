package com.LearnLC;

public class SearchRange {
    public static void findBinSea(int[] nums,int start,int end,int target,int[] goals){
        if(start > end) return;
        int mid = (start + end) / 2;
        if(nums[mid] > target) findBinSea(nums,start,mid - 1,target,goals);
        else if(nums[mid] < target) findBinSea(nums,mid + 1,end,target,goals);
        else {
            goals[0] = Math.min(mid,goals[0]);
            goals[1] = Math.max(mid,goals[1]);
            findBinSea(nums,start,mid - 1,target,goals);
            findBinSea(nums,mid + 1,end,target,goals);
        }
    }
    public static int[] searchRange(int[] nums, int target) {
        int[] goals = {Integer.MAX_VALUE,Integer.MIN_VALUE};
        findBinSea(nums,0,nums.length - 1,target,goals);
        goals[0] = goals[0] == Integer.MAX_VALUE ? -1:goals[0];
        goals[1] = goals[1] == Integer.MIN_VALUE ? -1:goals[1];
        return goals;
    }
    public static void main(String[] args){
        int[] nums = {5,7,7,8,8,9};
        searchRange(nums,3);
    }
}
