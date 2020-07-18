package com.LearnLC;


import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 *
 * 注意 指针移位就行了
 */
class ListNode{
    int val;
    ListNode next;
    ListNode(int x){ val = x ; }
}
public class SwapPairs {
    public static ListNode swapPairs(ListNode head) {
        if(head == null) return head;
        ListNode pre = null,last = head , next;
        while(last != null){
            next = last.next;
            if(next == null) break;

            last.next = next.next;
            next.next = last;
            if(last == head){
                //启动
                head = next;
            }
            else {
                pre.next = next;
            }
            pre = last;
            last = last.next;
            //next = last.next;
        }
        return head;
    }
    public static void main(String[] args){
        ListNode head = new ListNode(1);
//        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(4);
        head = swapPairs(null);
        while(head != null){
            System.out.print(" "+head.val);
            head = head.next;
        }
    }
}
