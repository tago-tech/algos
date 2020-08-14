package com.basicalgos;

import java.util.Deque;
import java.util.LinkedList;

public class ArraysProblem {
    //滑动窗口的最大值
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int index = 0;
        int[] maxArray = new int[nums.length - k + 1];
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0;i < nums.length;i++) {
            //淘汰队列中过期值
            while (!queue.isEmpty() && i - queue.peekFirst() >= k) {
                queue.pollFirst();
            }
            //更新队列中最大值
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.addLast(i);
            //输出窗口最大值
            if (i >= k - 1) {
                maxArray[index++] = nums[queue.peekFirst()];
            }
        }
        return maxArray;
    }

    //循环打印矩阵
    public static int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return new int[0];
        int m = matrix.length , n = matrix[0].length , idx = 0;
        int[] rs = new int[m * n];
        boolean[][] history = new boolean[m][n];
        int[][] tensor = {{0,1},{1,0},{0,-1},{-1,0}};
        int i = 0,j = 0 , tensorP = 0;
        while (idx < m * n) {
            rs[idx++] = matrix[i][j];
            history[i][j] = true;
            //切换方向
            if (i + tensor[tensorP][0] < 0 || j + tensor[tensorP][1] < 0 ||
                    i + tensor[tensorP][0] >= m || j + tensor[tensorP][1] >= n
                    || history[i + tensor[tensorP][0]][j + tensor[tensorP][1]]
            ) {
                tensorP++;
                tensorP %= tensor.length;
            }
            i = i + tensor[tensorP][0];
            j = j + tensor[tensorP][1];
        }
        return rs;
    }


}
