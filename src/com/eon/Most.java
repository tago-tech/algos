package com.eon;
import java.util.Arrays;
import java.util.Scanner;

public class Most {
    public static int[] mostLooong(int[] nums) {
        for (int i = 0;i < nums.length;i++) System.out.print("," + nums[i]);
        System.out.println();
        int maxCount = 1 , maxKey = nums[1];
        int i = 0;
        int count = 0;
        while (i < nums.length) {
            count = 1;
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                count++;
                if (count > maxCount) {
                    maxKey = nums[i];
                    maxCount = count;
                }
                i++;
            }
            i++;
        }
//        System.out.println("众数 = " + maxKey + " ,最多 = "+ maxCount);
        int[] rs = {maxCount,maxKey};
        return rs;
    }
    public static int f(int[] nums,int k) {
        int opNums = 0;
        Arrays.sort(nums);
        int[] meanCounts = mostLooong(nums);
        while (meanCounts[0] < k) {
            if (nums[0] < meanCounts[1]) {
                int temp = nums[0] + 1;
                int i = 0;
                while (i < nums.length && nums[i + 1] < temp) {
                    nums[i] = nums[i + 1];
                    i++;
                }
                //交换
                nums[i] = temp;
            }
            else if (nums[nums.length - 1] > meanCounts[1]) {
                int temp = nums[nums.length - 1] - 1;
                int i = nums.length - 1;
                while (i > -1 && nums[i - 1] > temp) {
                    nums[i] = nums[i - 1];
                    i--;
                }
                //交换
                nums[i] = temp;
            }
            meanCounts = mostLooong(nums);
            opNums++;
        }
        return opNums;
    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n =scn.nextInt();
        int k = scn.nextInt();
        int[] nums = new int[n];
        for (int i =0 ;i < nums.length;i++) nums[i] = scn.nextInt();
        System.out.println(f(nums,k));
    }
}
