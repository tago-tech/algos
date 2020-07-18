package com.LearnLC;

import java.util.*;

//利用BFS寻找 离 起点 最短的路径. 不管是 左右邻居 以及 同值点 都可以视为 BFS 上的路径.
public class MinJumps {
    public static int minJumps(int[] arr){
        //记录同值的索引
        Map<Integer, List<Integer>> map = new HashMap<>();
        //可跳跃标记
        boolean[] canJumped = new boolean[arr.length];
        Arrays.fill(canJumped,true);
        //访问标记
        //路径值标记
        int[] distances = new int[arr.length];
        Arrays.fill(distances,Integer.MAX_VALUE);
        //初始化同值列表
        for(int i = 0;i < arr.length;i++){
            if(!map.containsKey(arr[i])) map.put(arr[i],new ArrayList<>());
            map.get(arr[i]).add(i);
        }
        //初始化 BFS 队列
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        distances[0] = 0;
        while(!queue.isEmpty()){
            //取出对头元素
            int idx = queue.peek();
            queue.poll();
            //左跳
            if(idx > 0 && distances[idx - 1] == Integer.MAX_VALUE){
                distances[idx - 1] = distances[idx] + 1;
                queue.add(idx - 1);
            }
            //右跳
            if(idx < arr.length - 1 && distances[idx + 1] == Integer.MAX_VALUE){
                distances[idx + 1] = distances[idx] + 1;
                queue.add(idx + 1);
            }
            //同值跳
            if(canJumped[idx]){
                for(int sameIdxs : map.get(arr[idx])){
                    if(distances[sameIdxs] == Integer.MAX_VALUE){
                        distances[sameIdxs] = distances[idx] + 1;
                        queue.add(sameIdxs);
                        canJumped[sameIdxs] = false;
                    }
                }
            }


        }
        return distances[arr.length - 1];
    }
    public static void main(String[] args){
        int[] nums = {11,22,7,7,7,7,7,7,7,22,13};
        System.out.println(minJumps(nums));
    }
}
