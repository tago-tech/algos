package com.basicalgos;

import java.util.ArrayList;
import java.util.List;

public class RecursionProblems {
    /**
     * N皇后问题
     * 同行 、 同列 、 同对角线 的限制
     * 使用位运算加速
     * left 记录 反对角线 对下一行的影响，每次下移时，右移一位
     * right 同理
     * */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> rx = new ArrayList<>();
        //表示 正对角线 与 反对角线 的影响
        //寻找下一个皇后时, left >> 1,right << 1
        int left = 0 , right = 0;
        //可用位置
        int have = 0;
        for (int i = 0;i < n;i++) {
            have |= (1 << i);
        }
        int[] queues = new int[n];
        nextQueue(0,n,have,left,right,rx,queues);
        return rx;
    }
    public static int gitDigital (int num) {
        int idx = 0;
        while (((1 << idx) & num) == 0) {
            idx++;
        }
        return idx;
    }
    public static void nextQueue (int idx ,int n , int ok ,int lft , int rgh , List<List<String>> rx , int[] queues) {
        //边界条件判断
        if (idx >= n) {
            //将queque转换成字符串
            List<String> result = new ArrayList<>();
            for (int i = 0;i < n;i++) {
                String temp = "";
                for (int j = 0;j < n;j++) {
                    if (j == queues[i]) {
                        temp+= 'Q';
                    }
                    else {
                        temp += '.';
                    }
                }
                result.add(temp);
            }
            System.out.println(result.toString());
            rx.add(result);
            return;
        }
        //通过lft , rgh 的推算  + have => 可用位置
        int posts = -1;
        //求出可用有哪些
        posts &= ok;
        //求出刨除 斜对角线影响 有哪些
        posts &= ~lft;
        posts &= ~rgh;
        //寻找可用位置
        while (posts != 0) {
            //取出最右边 1
            int cur = posts & (-posts);
            queues[idx] = gitDigital(cur);
            //取出 posts 的 1 ， 并且施加到 left , right , hava 的影响中
            nextQueue(idx + 1,n,ok & (~cur) , (lft | cur) >> 1,(rgh | cur) << 1,rx,queues);
            //减去最右边 1
            posts = posts & (posts - 1);
        }
    }



}
