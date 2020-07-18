package com.LearnLC;

import java.util.Stack;

//单调栈的用法
public class MonotonicStack {
    //最简单的单调栈应用
    public static int[] dailyTemperatures(int[] T) {
        int[] waittings = new int[T.length];
        Stack<Integer> stack = new Stack<>();
        int idx = 0,rollIdx = 0;
        while(idx < T.length) {
            if (stack.isEmpty() || T[idx] < T[stack.peek()]) {
                stack.push(idx);
            }
            else {
                //持续弹出
                while (!stack.isEmpty() && T[stack.peek()] < T[idx]) {
                    rollIdx = stack.pop();
                    waittings[rollIdx] = idx - rollIdx;
                }
                stack.push(idx);
            }
            idx++;
        }

        while (!stack.isEmpty()) {
            rollIdx = stack.pop();
            waittings[rollIdx] = 0;
        }
//        for (int i : waittings) {
//            System.out.print(" "+i);
//        }
        return waittings;
    }

    //接雨水 - 42
    /**
     * 不能用洼地去计算,单个位置去计算,然后累加,因为洼地会有失效的地方
     * 当走到 index 时,index 处 能盛水的容量只与它 左边 和 右边的 最大值有关系, 且与最小的那个最大值有关系
     * 如果minMax > height ,则肯定能容纳 minMax - height 的水,否则是0容量.
     * 第一种方法,通过两个方向的遍历,计算出左方向和右方向的最大值.
     * 时间复杂度是 O(N),空间复杂度是 O(N)
     */
    public static int trap(int[] height) {
        int capitily = 0,max = 0,index = 0;
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];

        for (int i = 0;i < height.length;i++) {
            max = Math.max(max,height[i]);
            leftMax[i] = max;
        }
        max = 0;
        for (int i = height.length - 1;i > -1;i--) {
            max = Math.max(max,height[i]);
            rightMax[i] = max;
        }
        while (index < height.length) {
            //左边最大值
            max = index > 0 ? leftMax[index - 1] : 0;
            //右边最大值
            max = Math.min(max,index < height.length - 1 ? rightMax[index + 1] : 0);
            capitily += (max > height[index] ? max - height[index] : 0);
//            System.out.println("index = "+index+" val = "+height[index]);
            index++;
        }
        return capitily;
    }

    public static int trapAc(int[] height) {
        if (height == null || height.length < 3) return 0;
        int lMax = height[0],rMax = height[height.length - 1],l = 1,r = height.length - 2;
        int capitily = 0,minMax = 0;
        while (l <= r) {
            minMax = Math.min(lMax,rMax);
            if (lMax <= rMax) {
                capitily += Math.max(0,minMax - height[l]);
                lMax = Math.max(lMax,height[l]);
                l++;
            }
            else {
                capitily += Math.max(0,minMax - height[r]);
                rMax = Math.max(rMax,height[r]);
                r--;
            }
        }
        return capitily;
    }
    /*
    * 第二种方法比较巧妙.
    * lMax记录着左边开始的最大值,rMax记录右边开始的最大值.
    * 且最大值在移动的过程中只会增大,且受到最小的那个的制约.
    * 双向指针,分别从第二个和倒数第二个开始,因为首尾节点肯定不能成水.
    * 如果 lMax <= rMax时候,这时候 l指针的容水量已经确定,因为 最短的木板 lMax已经确定
    * 这时候移动 l 指针,并更新容量,更新 lMax 最大值.
    * 同理右端.
    * 当l == r 时,这个节点是需要计算的.
    * */
    public static void main(String[] args) {
//        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
//        dailyTemperatures(temperatures);
        int[] heights = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trapAc(heights));
    }
}
