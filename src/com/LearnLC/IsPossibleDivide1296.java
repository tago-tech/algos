package com.LearnLC;

import java.util.*;

public class IsPossibleDivide1296 {
    //首先将数组排序,从最小的元素开始组成一组,删除.一次性要删除第一个元素的个数
    //如果不存在后续的元素,则false,存在的话直接删除count个.
    //在删除的过程中用minKey记录下来最小的未被删除干净的元素,作为下一组的起点.
    //默认第二组的二起点是第一组第一个元素 + k,但是删除的过程中可能会出现第一组元素剩余的问题,用minKey解决
    public static boolean isPosssibleDivideMie(int[] nums,int k) {
        if (nums.length % k != 0) return false;
        //从小到大或者从大到小总能删干净
        Arrays.sort(nums);
        //Map作为记录元素剩余个数的工具
        HashMap<Integer,Integer> map = new HashMap<>();
        int minKey = 0;
        for (int i = 0;i < nums.length;i++) {
            if(!map.containsKey(nums[i]))
                map.put(nums[i],0);
            map.put(nums[i],map.get(nums[i]) + 1);
        }

        int idx = 0;
        while (idx < nums.length) {
            int count = map.get(nums[idx]);
            //默认下一组的起点是上一组第一个元素加K
            minKey = nums[idx] + k;
            for (int j = 0;j < k;j++) {
                int moveKey = nums[idx] + j;
                if (!map.containsKey(moveKey) || map.get(moveKey) < count) {
                    return false;
                }
                map.put(moveKey,map.get(moveKey) - count);
                //第一组出现剩余,minKey缩小
                if (map.get(moveKey) > 0)
                    minKey = Math.min(minKey,moveKey);
            }
            //移动idx到minKey对应的位置,因为数组已经升序,总能到达正确位置.
            while(idx < nums.length && nums[idx] < minKey) idx++;
        }
        return true;
    }
    public static boolean isPossibleDivideNLogN(int[] nums, int k) {
        if (nums.length % k != 0) return false;
        //首先将数组排序
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        for (int i = 0;i < nums.length;i++) {
            if (!treeMap.containsKey(nums[i]))
                treeMap.put(nums[i],0);
            treeMap.put(nums[i],treeMap.get(nums[i]) + 1);
        }
        while(!treeMap.isEmpty()){
            int item = treeMap.firstKey();
            int count = treeMap.get(item);
            for (int j = 0;j < k;j++){
                if (!treeMap.containsKey(item + j) || treeMap.get(item + j) < count) {
                    return false;
                }
                if (treeMap.get(item + j) == count) treeMap.remove(item + j);
                else treeMap.put(item + j,treeMap.get(item + j) - count);
            }
            System.out.println("Key = "+item);
        }
        return true;
    }
    //利用优先队列(最小堆)
    public static boolean isPossibleDivide(int[] nums, int k) {
        if(nums.length % k != 0) return false;
        //建立优先队列(最小堆),未知的复杂度
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int i = 0;i < nums.length;i++){
            queue.offer(nums[i]);
        }
        //循环元素O(N)的复杂度
        while (!queue.isEmpty()) {
            //取出最小的元素
            int item = queue.poll();
            for(int j = 1;j < k;j++) {
                //尝试删除元素O(N)的复杂度
                if(!queue.remove(item + j))
                    return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        int[] a = {13,14,15,7,8,9,20,21,22,4,5,6};
        System.out.println(isPosssibleDivideMie(a,3));
    }
}