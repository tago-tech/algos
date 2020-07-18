package com.LearnLC;
import com.LearnLC.SwapPairs.*;

import java.util.List;


public class ReverseKGroup {
    public static ListNode reverseKGroup(ListNode head, int k) {
        // 形如: pre -> p -> q -> **(若干个)** -> poinner
        if(head == null) return head;
        ListNode pre = null,p = head,q,poinner = head;
        int step = 0;
        //初始化poinner
        while(step < k && poinner != null){
            poinner = poinner.next;
            step++;
        }
        while( (step == k) || poinner != null){
            q = p.next;
            step = 1;
            while(step < k){
                p.next = q.next;
                if(pre == null){
                    q.next = head;
                    head = q;
                }
                else{
                    q.next = pre.next;
                    pre.next = q;
                }
                q = p.next;
                step++;
            }
            pre = p;
            p = p.next;

            //移动poinner
            step = 0;
            while(step < k && poinner != null){
                poinner = poinner.next;
                step++;
            }
        }
        return head;
    }

    //翻转链表
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode ln = head.next,nextLn = null;
        head.next = null;
        while (ln != null) {
            nextLn = ln.next;
            ln.next = head;
            head = ln;
            ln = nextLn;
        }
        return head;
    }
    public static boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        boolean fal = (n & (n - 1)) == 0;
        return fal;
    }
    //分割数组到两个子集
    public static boolean canPartition(int[] nums) {
        if (nums == null || nums.length <= 1) return false;
        //相当于把 nums数组里的元素放到背包里
        int sum = 0;
        for (int i = 0;i < nums.length;i++) sum += nums[i];
        if (sum % 2 == 1) return false;
        int[][] dp = new int[nums.length][sum / 2 + 1];
        //dp[i][j] 表示将[0..i]放到背包里产生的最大价值
        for (int j = 0;j <= sum / 2;j++) dp[0][j] = j >= nums[0] ? nums[0] : 0;
        for (int i = 1;i < nums.length;i++) {
            for (int j = 0;j <= sum / 2;j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i]) {
                    dp[i][j] = Math.max(dp[i][j],nums[i] + dp[i - 1][j - nums[i]]);
                }
            }
        }
        return dp[nums.length - 1][sum / 2] == sum / 2 ? true : false;
    }

    public static int canPartition2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        //相当于把 nums数组里的元素放到背包里
        int sum = 0;
        for (int i = 0;i < nums.length;i++) sum += nums[i];
        int[] dp = new int[sum / 2 + 1];
        //dp[i][j] 表示将[0..i]放到背包里产生的最大价值
        for (int j = 0;j <= sum / 2;j++) dp[j] = j >= nums[0] ? nums[0] : 0;
        for (int i = 1;i < nums.length;i++) {
            for (int j = sum / 2;j >= 0;j--) {
                dp[j] = dp[j];
                if (j >= nums[i]) {
                    dp[j] = Math.max(dp[j],nums[i] + dp[j - nums[i]]);
                }
            }
        }
        return Math.abs(sum - 2 * dp[sum / 2]);
    }


    public static void main(String[] args){
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        hestNode res = reverseKGroup(head,1);
        int i = -1;
        System.out.println(Integer.toBinaryString(i));
        int j = 6;
        System.out.println(Integer.toBinaryString(j));
        System.out.println(Integer.toBinaryString(i & j));

        System.out.println(Integer.toBinaryString(11));
        System.out.println(Integer.toBinaryString(~11 + 1));
        System.out.println(Integer.toBinaryString(-11));

        int[] a = {2,7,4,1,8,1};
        System.out.println(canPartition2(a));


    }
}
