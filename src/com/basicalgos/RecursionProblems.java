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


    /**
     * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
     * 暴力解法（回溯解法）
     * 每个元素可被分为任意一个集合中
     * 总共有K个集合
     * 难点在于剪枝：
     *     1.记录每个集合当前的总和，若超过肯定不满足条件
     *     2.假设当前元素为 i ，前 i - 1 个元素被划分到的集合最大序号为 x , 对于 i 元素，划分到 i + 1 等价于划分到 i + 任意值;
     *      (因为集合只是一个序号而已)
     * */
    public static boolean canPartitionKSubsets(int[] nums, int k) {
        //异常检测 及 简单筛选
        if (nums == null || nums.length < k) return false;
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        //不能够被整除,直接false
        if (sum % k != 0) return false;
        int n = nums.length;
        //currents数组表示第k个集合中目前总和
        int[] currents = new int[k];
        //每个集合的理想值
        int target = sum / k;
        boolean success = dfs(nums,0,k,currents,target,-1);
        return success;
    }
    /**
     * 每个元素都有机会被划分到任意一个集合 with canPartitionKSubsets
     * */
    public static boolean dfs (int[] nums,int idx,int k,int[] currents,int target,int max) {
        int n = nums.length;
        //终止条件
        if (idx >= n) {
            //若发现有的集合元素不等于target则表示此情况不满足
            for (int i = 0;i < k;i++) {
                if (currents[i] != target) {
                    return false;
                }
            }
            return true;
        }
        //将当前元素 IDX 划分到 i 集合
        for (int i = 0;i < k;i++) {
            //当前集合若和大于理想值则进行剪枝
            if (currents[i] + nums[idx] > target) {
                continue;
            }
            //在集合i中加入 i 元素进行回溯
            currents[i] += nums[idx];
            if (dfs(nums,idx + 1,k,currents,target,Math.max(max,i))) {
                return true;
            }
            //从集合 i 中删除当前 idx 元素，为了便于下次分配集合
            currents[i] -= nums[idx];
            //max记录当前最大的集合序号，若超过这个最大值，后面的记录没有任何意义
            //因为集合只是一个序号，要么把当前元素与之前的元素分到一个集合中，要么分到一个新的集合中。
            //况且对于新的集合都是“陌生的”，都是等价的。
            if (i > max) {
                return false;
            }
        }
        return false;
    }


}
