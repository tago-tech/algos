package com.LearnLC;

import java.util.*;
public class Main {
    /**
     * 第一个缺失的正数
     * [1...l] 表示当前已经出现的正数范围(现实)
     * [1...r] 表示当前期望能够出现的正数范围(希望)
     * 初始时，现实出现的范围为[1...0] => l = 0 , 而希望中我们会有 [1...n]
     * 通过在O(N)的遍历，我们让梦想向现实屈服，直到二者相等。
     * 我们以 nums[l]为衡量标准
     * if (nums[l] == l + 1) 表示正是我们期望的下一个数
     * else if (nums[l] > r) 表示这个数超出我们的预期，因为多个一个[r...+Max] 范围的数，相对应
     *  就导致我们的[1...r] 不能够得到满足，（此消彼长），因此 r-- ,此时需要将nums[r - 1] 移到
     *  l处，因为r虽然缩小了，但nums[r - 1] 可能还有用。
     * else if (nums[l] <= l) 此时这个数已经出现过了，此消彼长，我们的期望不得不降低，r--，并将
     *  nums[r - 1]处数字移动到l处
     * //若以上情况都不满足，出现的数字肯定在[l + 1,r]中，如果按升序来放，应该把nums[l] 放到nums[l]-1
     *  处,但是如果nums[l] - 1处已经有一个nums[l]了，则表示数字重复了，我们的期望不得不减小。同时将
     *  nums[r - 1] 移动到l处，r--; 而！如果这个数字没有重复直接交换一下就行
     * else if (nums[nums[l] - 1] == nums[l]) ...
     * else { swap () }
     * 直到循环结束
     * */
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length , l = 0 , r = n;
        while (l < r) {
            if (nums[l] == l + 1) {
                l++;
            }
            else if (nums[l] > r || nums[l] <= l) {
                r--;
                nums[l] = nums[r];
            }
            else if (nums[nums[l] - 1] == nums[l]) {
                r--;
                nums[l] = nums[r];
            }
            else {
                int temp = nums[l];
                nums[l] = nums[nums[l] - 1];
                nums[temp - 1] = temp;
            }
        }
        return l + 1;
    }

    public static int f(int[] works,int[] execs,int n) {
        // 0 休息 1 工作 2 锻炼
        int[] dp = new int[3];
        int a , b ,c;
        for (int i = 0;i < n;i++) {
            a = dp[0];
            b = dp[1];
            c = dp[2];
            //今天选择休息
            dp[0] = (1 + Math.min(a,Math.min(b,c)));
            //今天选择 工作
            if (works[i] == 1) {
                dp[1] = Math.min(a,c);
            }
            else {
                dp[1] = Integer.MAX_VALUE;
            }
            if (execs[i] == 1) {
                dp[2] = Math.min(a,b);
            }
            else {
                dp[2] = Integer.MAX_VALUE;
            }
        }
        int min = Integer.MAX_VALUE;

        min = Math.min(min,dp[0]);
        min = Math.min(min,dp[1]);
        min = Math.min(min,dp[2]);
        return min;
    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] works = new int[n];
        int[] exec = new int[n];
        for (int i = 0;i < n;i++) works[i] = scn.nextInt();
        for (int i = 0;i < n;i++) exec[i] = scn.nextInt();
        System.out.println(f(works,exec,n));

    }
}
