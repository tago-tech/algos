package com.LearnLC;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
* 数组类问题
* */
public class ArrayProblems {

    public static int subarraySum(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        int count = 0 , sum = 0;
        for (int i =0;i < nums.length;i++) {
            sum += nums[i];
            count += map.getOrDefault(sum - k,0);
            if (!map.containsKey(sum))
                map.put(sum,0);
            map.put(sum,map.get(sum) + 1);
        }
        return count;
    }
    //lc:862 和至少为 K ，且长度最短子数组的数目 ? 明天做
    public static int shortestSubarray(int[] A, int K) {
        if (A == null || A.length == 0) return 0;
        int count = 0,len = -1,sum = 0;
        //key 表示累加和 ，Integer 表示出现最近的Index
        TreeMap<Integer,Integer> map = new TreeMap<>();
        map.put(0,-1);
        for (int i = 0;i < A.length;i++) {
            sum += A[i];
            Set<Integer> s = map.headMap(sum - K + 1).keySet();
            //存在这样的子数组
            for (int key : s) {
                    int temLen = i - map.get(key);
                    len = len == -1 ? temLen : Math.min(len,temLen);
            }
            //持续更新 sum 最近的
            map.put(sum,i);
        }
        return len;
    }

    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0,mulRs = 1;
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,-1);
        return k;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            int idx = 1;
            @Override
            public void run() {
                try {
                    while (idx < 1000000) {
                        idx++;
                        Thread.sleep(2000);
                    }
                }
                catch (InterruptedException e) {

                }
                finally {

                }
            }
        });
        t.start();
        t.join();
        System.out.println("运行结束");
    }
}
