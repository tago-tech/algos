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

}
