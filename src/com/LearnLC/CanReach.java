package com.LearnLC;


public class CanReach {
    public static boolean travel(int[] arr,int start,boolean[] traveled){
        if(arr[start] == 0) return true;
        boolean leftCanReach = false;
        boolean rightCanReach = false;
        traveled[start] = true;
        //左边界判定 及 旅行标记判定
        if(start - arr[start] > -1 && !traveled[start - arr[start]]) leftCanReach = travel(arr,start - arr[start],traveled);
        //右边界判定 及 旅行标记判定
        if(start + arr[start] < arr.length && !traveled[start + arr[start]]) rightCanReach = travel(arr,start + arr[start],traveled);
        traveled[start] = false;
        //当前节点是否能到达受影响于 左右两个相邻的节点
        return  leftCanReach || rightCanReach ;
    }
    public static boolean canReach(int[] arr, int start) {
        boolean[] traveled = new boolean[arr.length];
        return travel(arr,start,traveled);
    }
    public static void main(String[] args){
        int[] nums = {4,2,3,0,3,1,2};
        System.out.println(canReach(nums,5));
    }
}
