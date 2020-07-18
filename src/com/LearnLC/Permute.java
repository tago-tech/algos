package com.LearnLC;

import java.util.ArrayList;
import java.util.List;

public class Permute {
    public static void travle(List<List<Integer>> resultCollections,int[] nums,List<Integer> used,int process){
        if( process > nums.length - 1 ){
            List<Integer> temp = new ArrayList<Integer>();
            for(int idx = 0 ; idx < nums.length ; idx++) temp.add(0);
            for(int idx = 0 ; idx < nums.length ; idx++) temp.set(used.get(idx),nums[idx]);
            resultCollections.add(temp);
        }
        for(int idx = 0; idx < used.size() ;idx++){
            if(used.get(idx) == -1){
                used.set(idx,process);
                travle(resultCollections,nums,used,process + 1);
                used.set(idx,-1);
            }
        }
    }
    public static List<List<Integer>> permute(int[] nums) {
        int process = 0;
        List<List<Integer>> resultCollections = new ArrayList<>();
        List<Integer> used = new ArrayList<Integer>();
        for(int idx = 0 ; idx < nums.length ; idx++) used.add(-1);
        travle(resultCollections,nums,used,0);
        return resultCollections;
    }
    public static void main(String[] args){
//        int[] nums = {1,2,3};
        int[] nums = {1,1,2};
        List<List<Integer>> resultCollections = permute(nums);
        for(List<Integer> result : resultCollections) System.out.println(result);
    }
}
