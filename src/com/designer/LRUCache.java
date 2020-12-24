package com.designer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 采用双向链表维护插入访问顺序
 */
public class LRUCache {

    Map<Integer,DLinkNode> map;

    //双向链表
    class DLinkNode {

        DLinkNode prev;

        Integer value;

        Integer key;

        DLinkNode next;

        public DLinkNode (Integer key,Integer val) {
            this.value = val;
            this.key = key;
        }
    }

    DLinkNode head;

    DLinkNode tail;

    public int capacity = 0;

    //当前容量
    public int size = 0;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        head = new DLinkNode(null,null);
    }

    public int get(int key) {

        //若存在则返回，否则返回-1
        if (!map.containsKey(key)) return -1;

        //设为最近更新
        DLinkNode node = map.get(key);

        remove(node);

        makeFrist(node);

        return map.get(key).value;
    }

    public void put(int key, int value) {
        DLinkNode node = null;
        if (map.containsKey(key)) {

            node = map.get(key);

            node.value = value;

            remove(node);

            makeFrist(node);
        }
        else {
            node = new DLinkNode(key,value);

            map.put(key,node);

            makeFrist(node);

            //插入，size++
            size++;

            if (size > capacity && size > 0) {

                map.remove(tail.key);

                remove(tail);

                size--;
            }
        }

    }

    private void remove (DLinkNode node) {

        if (node == null) return;

        node.prev.next = node.next;

        if (node.next != null) {
            node.next.prev = node.prev;
        }

        if (node == tail)  {
            tail = tail.prev == head ? null : tail.prev;
        }
    }

    private void makeFrist (DLinkNode node) {
        if (node == null) return;

        if (tail == null) {
            tail = node;
        }

        node.prev = head;

        node.next = head.next;

        if (head.next != null) {
            head.next.prev = node;
        }

        head.next = node;
    }
}
