package com.LearnLC;
    /**
     * 复制含有随机指针的链表
     * */
public class CopyListWithRandomPointer {

    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        Node n = head,next = null,newN = null,newHead = null;
        //每个节点之后插入副本节点
        while (n != null) {
            next = n.next;
            newN = new Node(n.val);
            newN.next = next;
            n.next = newN;
            n = next;
        }
        n = head;
        newHead = head.next;
        while (n != null) {
            //n每次走两步
            next = n.next.next;
            newN = n.next;
            newN.random = n.random == null ? null : n.random.next;
            n = next;
        }
        n = head;
        while (n != null) {
            newN = n.next;
            next = newN.next;
            newN.next = next == null ? null : next.next;
            n.next = next;
            n = next;
        }
        return newHead;
    }
    public static void main(String[] args) {
        Node t = new Node(1);
        t.random = t;
        t.next = null;
        System.out.println(copyRandomList(t).val);
        System.out.println(copyRandomList(t).next);
        System.out.println(copyRandomList(t).random.val);
    }
}
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
