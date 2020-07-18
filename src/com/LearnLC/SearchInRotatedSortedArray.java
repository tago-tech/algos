package com.LearnLC;

/*
* 1.利用二分查找,找到轴心o(logN)
* 2.利用二分查找在局部范围查找元素o(logN)
* */
public class SearchInRotatedSortedArray {
    public static int findRotatedSmallest(int[] nums,int left,int right){
        if(nums[left] < nums[right]) return left;
        while(left <= right){
            int mid = (left + right) / 2;
            if(nums[mid] > nums[mid + 1]) return mid + 1;
            if(nums[mid] < nums[left]) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return left;
    }
    //在left ~ right范围内进行二分搜索
    public static int find(int[] nums,int left,int right,int target){
        int mid = left;
        while(left <= right){
            mid = (left + right) / 2;
            if(nums[mid] == target) return mid;
            if(nums[mid] < target){
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
    public static int search(int[] nums,int target){
        if(nums.length == 0) return -1;
        if(nums.length == 1) return nums[0] == target ? 0 : -1;
        int left = 0,right = nums.length - 1;
        //未发生翻转情况下
        if(nums[left] < nums[right]) return find(nums,left,right,target);
        //找到旋转的最小元素下标
        int rotateMinIndex = findRotatedSmallest(nums,left,right);
        //System.out.println("rotateMinIndex = "+ rotateMinIndex);
        //进行搜索
        if(target >= nums[0]){
            return find(nums,0,rotateMinIndex - 1,target);
        }
        else {
            return find(nums,rotateMinIndex,nums.length - 1,target);
        }
    }
    public static void main(String[] args){
        int[] nums = {4,5,6,7,1,2,3};
        System.out.println(search(nums,7));
    }
}
