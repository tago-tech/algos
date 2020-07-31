package com.company;

import java.util.HashSet;
import java.util.Stack;

public class Tester {
    public static ListNode reverseList(ListNode head) {
        ListNode next = head.next,cur = head.next;
        head.next = null;
        while (next != null) {
            next = cur.next;
            cur.next = head;
            head = cur;
            cur = next;
        }
        return head;
    }
    public static int reversePairs(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stackDump = new Stack<>();
        int count = 0;
        for (int i = 0;i < nums.length;i++) {
            while (!stack.isEmpty() && stack.peek() > nums[i]) {
                stackDump.push(stack.pop());
                count++;
            }
            stack.push(nums[i]);
            while (!stackDump.isEmpty()) {
                stack.push(stackDump.pop());
            }
        }
        return count;
    }
    public static int reversePairs2(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int[] copy = new int[nums.length];
        for (int i = 0;i < nums.length;i++) copy[i] = nums[i];
        int[] temp = new int[nums.length];
        return mSort(copy,0,nums.length - 1,temp);
    }
    public static int mSort(int[] nums,int left,int right,int[] temp) {
        if (right - left < 1) return 0;
        int mid = (left + right) / 2;
        int leftCount = mSort(nums,left,mid,temp);
        int rightCount = mSort(nums,mid + 1,right,temp);
        //这个时候已经有序了
        if (nums[mid] <= nums[mid + 1]) {
            return leftCount + rightCount;
        }
        //merge
        int cross = merge(nums,left,right,temp);

        return leftCount + rightCount + cross;
    }
    public static int merge(int[] nums,int left,int right,int[] temp) {
        int lptr = left,mid = (left + right) / 2 + 1, rptr = mid;
        int tempIdx = left , count = 0;
        while (lptr < mid && rptr <= right) {
            if (nums[lptr] <= nums[rptr]) {
                temp[tempIdx++] = nums[lptr++];
            }
            else {
                temp[tempIdx++] = nums[rptr++];
                count += mid - lptr;
            }
        }
        while (lptr < mid) {
            temp[tempIdx++] = nums[lptr++];
            //count += rptr - mid;
        }
        while (rptr <= right) {
            temp[tempIdx++] = nums[rptr++];
        }

        while (left <= right) {
            nums[left] = temp[left];
            left++;
        }
        return count;
    }
    public static void reverseString(char[] s) {
        int left = 0,right = s.length - 1;
        char temp;
        while (left < right) {
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        if (s == null || s.length() == 0) return 0;
        HashSet<Integer> set = new HashSet<>();
        int i = 0,j = 0;
        char[] chrs = s.toCharArray();
        while (i < chrs.length && j < chrs.length) {
            if (!set.contains(chrs[j] - ' ')) {
                set.add(chrs[j] - ' ');
                max = Math.max(set.size(),max);
                j++;
            }
            else {
                set.remove(chrs[i] - ' ');
                i++;
            }
        }
        return max;
    }
    public static int partilSort2(int[] nums,int left,int right,int k) {
        int temp = nums[left];
        int sl = left,sr = right;
        while (left < right) {
            while (left < right && nums[right] >= temp) right--;
            if (left < right && nums[right] < temp) nums[left] = nums[right];
            while (left < right && nums[left] < temp) left++;
            if (left < right && nums[left] >= temp) nums[right] = nums[left];
        }
//        assert left == right
        nums[right] = temp;
        if (right + 1 == k) return temp;
        //第一个区间是 sr - > left
        if (left < k) {
            if (left + 1 <= sr)
                return partilSort2(nums,left + 1,sr,k);
            else
                return -1;
        }
        else {
            if (left >= sl)
                return partilSort2(nums,sl,left - 1,k);
            else
                return -1;
        }
    }

}
