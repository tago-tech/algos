package com.LearnLC;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 */
public class ThreeSum {
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
        public static List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> ans = new ArrayList();
            //首先对数组进行排序
            Arrays.sort(nums);
            //固定nums[i],双指针扫描尾部子数组
            int idx = 0;
            int right,left;
            while( idx < nums.length ){
                //最小元素已大于0,因数组升序故退出
                if( nums[idx] > 0 ) break;
                //初始化 双指针
                left = idx + 1;
                right = nums.length - 1;
                //循环搜索
                while(right > left){
                    //移动指针
                    while(right > left && nums[left] + nums[right] + nums[idx] > 0) right--;
                    while(right > left && nums[left] + nums[right] + nums[idx] < 0) left++;
                    //l < r 条件 避免 l == r 时,
                    if( left < right && nums[left] + nums[right] + nums[idx] == 0 ){
                        //push
                        List<Integer> subAns = new ArrayList();
                        subAns.add(nums[idx]);
                        subAns.add(nums[left]);
                        subAns.add(nums[right]);
                        ans.add(subAns);
//                        showLists(ans);
                        left++;
                        right--;
                        //指针元素去重
                        while( left < right && nums[left] == nums[left - 1]) left++;
                        while( left < right && nums[right] == nums[right + 1]) right--;
                    }
                }

                idx++;
                //nums[idx]去重
                while( idx < nums.length && nums[idx] == nums[idx - 1] ) idx++;
            }
        return ans;
    }
    public static void main(String[] args){
        int[] nums = {-1, 0, 1, 2, -1, -4};
        threeSum(nums);
    }
}
