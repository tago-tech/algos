package com.LearnLC;

/**
 * 盛水最多的容器
 * 双指针法
 * 因为两端距离缩短时,需要依靠height增加提高 area 值;
 * 总是朝最长的一端移动
 * */
public class MaxArea {

    public static int area(int[] height,int left,int right){
        return (right - left) * Math.min(height[left],height[right]);
    }

    public static int maxArea(int[] height) {
        int zleft = 0;
        int zright = height.length - 1;
        int maxArea = 0;
        while(zleft < zright){
            maxArea = area(height,zleft,zright) > maxArea ? area(height,zleft,zright) : maxArea;
            if(height[zleft] < height[zright]) zleft++;
            else zright--;
        }
        return maxArea;
    }
    public static void main(String[] args){
        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(height));
    }

}
