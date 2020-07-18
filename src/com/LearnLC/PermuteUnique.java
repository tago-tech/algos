package com.LearnLC;

import java.util.*;

public class PermuteUnique {
    public static void travle(List<List<Integer>> resultCollections,int[] nums,List<Integer> used,int process){
        if( process > nums.length - 1 ){
            List<Integer> temp = new ArrayList<Integer>();
            for(int idx = 0 ; idx < nums.length ; idx++) temp.add(0);
            for(int idx = 0 ; idx < nums.length ; idx++) temp.set(used.get(idx),nums[idx]);
            resultCollections.add(temp);
            return;
        }
        int idx = 0;
        while( idx < used.size()){
            if(used.get(idx) == -1){
                //System.out.println("used set "+idx +" = "+process);
                used.set(idx,process);
                travle(resultCollections,nums,used,process + 1);
                used.set(idx,-1);
            }
            idx++;
            //去重
            while( idx < used.size() && nums[idx] == nums[idx - 1] && used.get(idx) == -1 && used.get(idx - 1) == -1 )  idx++;
        }
    }
    public static List<List<Integer>> permuteUnique(int[] nums) {
        int process = 0;
        //数组排序便于去重
        Arrays.sort(nums);

        List<List<Integer>> resultCollections = new ArrayList<>();
        List<Integer> used = new ArrayList<Integer>();
        for(int idx = 0 ; idx < nums.length ; idx++) used.add(-1);

        //为了去重而剪枝
        travle(resultCollections,nums,used,0);

        return resultCollections;
    }
    public static void main(String[] args){
        int[] nums = {1,1,2};
        List<List<Integer>> resultCollections = permuteUnique(nums);
        for(List<Integer> result : resultCollections) System.out.println(result);
    }
}
