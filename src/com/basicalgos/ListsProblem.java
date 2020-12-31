package com.basicalgos;

import com.datastructure.IPair;
import com.datastructure.ListNode;

import java.util.*;

public class ListsProblem {

    //约瑟夫环


    /**
     * 给你一个链表的头节点 head，请你编写代码，反复删去链表中由 总和 值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
     * method:
     *  1.sumRecords:Map<int,int> 记录出现过的和坐标，下次遍历时碰到出现过的和表示此段为0,可能作为被删除的段
     *  2.neededRemove:Set<Int> 记录删除过的数字，第二次线性遍历之后过滤;
     *  最坏时间复杂度 O(N2),空间复杂度O(N)
     * @param head
     * @return
     */
    public static ListNode removeZeroSumSublists(ListNode head) {

        if (head == null) return head;

        Map<Integer,Integer> sumRecords = new HashMap();

        Set<Integer> neededRemove = new HashSet<>();

        ListNode proxyOfHead = head;

        int index = 0 , sum = 0;

        sumRecords.put(0,-1);

        while (proxyOfHead != null) {

            sum += proxyOfHead.val;

            if (sumRecords.containsKey(sum) && !neededRemove.contains(sumRecords.get(sum) + 1)) {

                int start = sumRecords.get(sum) + 1;

                while (start <= index) {
                    neededRemove.add(start++);
                }
            }

            sumRecords.put(sum,index++);

            proxyOfHead = proxyOfHead.next;
        }

        proxyOfHead = new ListNode(Integer.MIN_VALUE);

        ListNode tail = proxyOfHead;

        index = 0;

        while (head != null) {

            ListNode next = head.next;

            if (!neededRemove.contains(index)) {

                tail.next = head;

                tail = head;

                tail.next = null;
            }

            head = next;

            index++;
        }

        return proxyOfHead.next;
    }

    /**
     * 分发糖果
     * 每个孩子至少分配到 1 个糖果。
     * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {

        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        int n = ratings.length;

        int left = 0 , right = 0;

        boolean on = true;

        int sum = 0;

        int i = 0;

        while (i < n) {
            if (on) {
                left++;
                //需要终止上坡过程
                if (i == n - 1 || ratings[i] >= ratings[i + 1]) {
                    on = false;
                    right = 0;
                    continue;
                }
                i++;
            }
            else {
                right++;
                //需要终止下坡过程
                if (i == n - 1 || ratings[i] <= ratings[i + 1]) {
                    sum += sumOfN(left) + sumOfN(right) - Math.min(left,right);
                    right = 0;
                    left = 0;
                    on = true;
                    if (i == n - 1) break;
                    if (i < n - 1 && ratings[i] == ratings[i + 1]) i++;
                    continue;
                }
                i++;
            }
        }

        for (i = 1;i < n - 1;i++) {
            if (ratings[i] < ratings[i - 1] && ratings[i] < ratings[i + 1]) {
                sum -= 1;
            }
        }
        return sum;
    }

    public static int sumOfN (int n) {
        return n * (n + 1) / 2;
    }

}
