package com.LearnLC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
    public static void showArray(int[] nums){
        System.out.print("Array:[");
        for(int i : nums) System.out.print(" "+i+",");
        System.out.print("]\n");
    }
    public static void showLists(List<List<Integer>> ans){
        System.out.print("Lists : {\n");
        for(List<Integer> li : ans){
            System.out.print("\t [ ");
            for(int item : li){
                System.out.print(""+item+", ");
            }
            System.out.print("], \n");
        }
        System.out.print("}\n");
    }
    public static List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> resultCollections = new ArrayList<>();
        if(nums.length < 4) return resultCollections;
        int a,b,c,d;
        //数组排序
        Arrays.sort(nums);
        a = 0;
        while (a < nums.length){

            b = a + 1;
            //移动 B 指针
            while( b < nums.length){

                c = b + 1;
                d = nums.length - 1;

                //移动 C D
                while( c < d ){
                    while(c < d && nums[a] + nums[b] + nums[c] + nums[d] > target ) d--;
                    while(c < d && nums[a] + nums[b] + nums[c] + nums[d] < target ) c++;
                    if( c < d && nums[a] + nums[b] + nums[c] + nums[d] == target){
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[a]);
                        temp.add(nums[b]);
                        temp.add(nums[c]);
                        temp.add(nums[d]);
                        resultCollections.add(temp);

                        c++;
                        d--;
                        // C D 去重
                        while(c < d && nums[c] == nums[c - 1]) c++;
                        while(c < d && nums[d] == nums[d + 1]) d--;
                    }
                }

                b++;
                // B 指针去重
                while( b < nums.length && nums[b] == nums[b - 1]) b++;
            }

            a++;
            while(a < nums.length && nums[a] == nums[a - 1]) a++;
        }

        return resultCollections;
    }
    public static void main(String[] args){
        int[] nums = {1,-2,-5,-4,-3,3,3,5};
        showLists(fourSum(nums,-11));
    }
}
