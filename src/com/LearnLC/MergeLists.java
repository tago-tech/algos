package com.LearnLC;

//from  import ListNode

import com.LearnLC.SwapPairs.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.List;

public class MergeLists {
    //合并 N 个链表
    public static ListNode mergeKLists(ListNode[] lists){
        if(lists.length == 0) return null;
//        //分治合并(利用函数栈)
//        return merge2Paart(lists,0,lists.length - 1);
        ListNode[] result = new ListNode[lists.length];
        for(int i = 0 ;i < result.length; i++) result[i] = lists[i];
        int capility = result.length - 1,distance = 1;

        while(capility > 0){
            int current = 0;
            for(int i = 0;i <= capility;i += distance * 2){
                int j = i + distance;
                if(j > capility) result[current++] = result[i];
                else result[current++] =  merge2List(result[i],result[j]);
            }
            //更新capility
            capility = current - 1;
            distance++;
        }
        return result[0];
    }
    public static ListNode merge2Paart(ListNode[] lists,int start,int end){
        if(start == end) return lists[start];
        if(end - start == 1) return  merge2List(lists[start],lists[end]);
        if(end > start){
            int mid = (start + end) / 2;
            return merge2List(merge2Paart(lists,start,mid),merge2Paart(lists,mid + 1,end));
        }
        return null;
    }
    //两两合并链表
    public static ListNode merge2List(ListNode head1, ListNode head2){
        if(head1 == null) return head2;
        if(head2 == null) return head1;
        //head1 != null && head2 != null
        //合并到 head1 上
        ListNode head,pre = null;
        ListNode p = head1,q = head2;

        //首先处理头结点
        if(p.val < q.val){
            head = p;
            p = p.next;
        }
        else{
            head = q;
            q = q.next;
        }
        pre = head;
        while(p != null && q != null){
            if(p.val < q.val){
                pre.next = p;
                p = p.next;
            }
            else{
                pre.next = q;
                q = q.next;
            }
            pre = pre.next;
        }
        // 剩余两种情况: 1 Head1 为空 head2 不为空 和 2 head1 不为空 head2 为空
        if(p != null) pre.next = p;
        if(q != null) pre.next = q;
        return head;
    }

    //简单划分链表 - 86
    public static ListNode partition(ListNode head, int x) {
        ListNode less = null,than = null,next = null,lessMove = null,thanMove = null;

        while (head != null) {
            next = head.next;
            head.next = null;
            if(head.val < x) {
                //添加到less列表中
                if (lessMove == null) {
                    less = head;
                    lessMove = head;
                }
                else {
                    lessMove.next = head;
                    lessMove = lessMove.next;
                }
            }
            else {
                if (thanMove == null) {
                    than = head;
                    thanMove = head;
                }
                else {
                    thanMove.next = head;
                    thanMove = thanMove.next;
                }
            }
            head = next;
        }
        if (less == null) {
            return than;
        }
        else {
            head = less;
            while (head.next != null) {
                head = head.next;
            }
            head.next = than;
            return less;
        }
    }

    //重排链表 - 143
    public static void reorderList(ListNode head) {

    }

    public static void main(String[] args){
        int distance = 1;
        int[] results = new int[9];
        int capility = results.length - 1;
        while(capility > 0){
            System.out.println();
            int current = 0;
            for(int i = 0 ; i <= capility ; i += distance * 2){
                int j = i + distance;
                if(j > capility) System.out.print("idx ="+(current++)+" "+i + " ");
                else System.out.print("idx ="+(current++)+" "+ "["+i+" "+j+"]");
            }
            capility = current - 1;
//            distance++;
            System.out.println("容量="+capility);
        }
    }
}
