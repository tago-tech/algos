package com.LearnLC;

import java.util.HashMap;
import java.util.Map;

public class LongestConsecutive {
    public static int merge(HashMap<Integer,Integer> map,int less,int more){
        int left = less - map.get(less) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left,len);
        map.put(right,len);
        return len;
    }
    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        int maxLen = 1;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < nums.length;i++) {
            //若未出现当前数字,若出现过的话对序列长度的增加没有好处
            if(!map.containsKey(nums[i])){
                map.put(nums[i],1);
                //num[i] - 1存在的话,num[i]肯定是第一次出现,也就是说num[i] - 1形成的序列 右部分肯定是缺少 num[i]的
                if(map.containsKey(nums[i] - 1)) {
                    maxLen = Math.max(maxLen,merge(map,nums[i] - 1,nums[i]));
                }
                //num[i] + 1存在的话,同理.
                if(map.containsKey(nums[i] + 1)) {
                    maxLen = Math.max(maxLen,merge(map,nums[i],nums[i] + 1));
                }
            }
        }
        return maxLen;
    }
    public static void main(String[] args){
        int[] nums = {0};
        System.out.println(longestConsecutive(nums));
    }
}
