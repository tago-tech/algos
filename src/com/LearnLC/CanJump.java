package com.LearnLC;

public class CanJump {
    //动态规划 O(n2)
    public static boolean canJump(int[] nums) {
        int[] maxTravles = new int[nums.length];
        int tryMove = 0;
        for(int idx = nums.length - 2;idx >= 0 ;idx--){
            maxTravles[idx] = idx + nums[idx];
            tryMove = 1;
            while(tryMove <= nums[idx] && idx + tryMove < nums.length && maxTravles[idx] < nums.length - 1){
                maxTravles[idx] = Math.max(maxTravles[idx],maxTravles[idx + tryMove]);
                tryMove++;
            }
        }
        return maxTravles[0] >= nums.length - 1 ? true : false;
    }
    //贪心算法,总是左移. O(n)
    public static boolean canJump2(int[] nums){
        int leftCanJump = nums.length - 1;
        for(int i = nums.length - 1;i >= 0;i--){
            if(i + nums[i] >= leftCanJump){
                leftCanJump = i;
            }
        }
        return leftCanJump == 0;
    }
    public static void main(String[] args){
        int[] nums = {2,3,1,1,4};
        System.out.println(canJump2(nums));
    }
}
