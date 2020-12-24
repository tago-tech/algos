package com.designer;

import javax.xml.soap.Node;
import java.util.*;

public class LFUCache {

    int size;

    int capacity;

    Map<Integer,KV> map;

    class KV {
        Integer value;

        Integer key;

        KV prev;

        KV next;

        DLinkNode currentPosition;

        public KV(DLinkNode currentPosition,Integer value, Integer key) {
            this.value = value;
            this.key = key;
            this.currentPosition = currentPosition;
        }
    }

    class DLinkNode {

        DLinkNode prev;

        DLinkNode next;

        Integer count;

        KV head;

        KV tail;


        public DLinkNode(Integer count) {
            this.head = new KV(this,null,null);

            this.count = count;
        }

        private void unLinkKV (KV element) {

            if (element == null) return;

            element.currentPosition = null;

            if (element == tail) {
                tail = tail.prev == head ? null : tail.prev;
            }

            element.prev.next = element.next;

            if (element.next != null) {
                element.next.prev = element.prev;
            }
        }

        /**
         * 增加元素时候
         * @param element
         */
        public void linkKV (KV element) {

            if (element == null) return;

            element.currentPosition = this;

            element.next = null;

            if (this.tail == null) {

                this.tail = element;

                element.prev = this.head;

                this.head.next = element;

                return;
            }

            element.prev = this.tail;

            this.tail.next = element;

            this.tail = element;
        }
    }

    DLinkNode head;

    DLinkNode tail;

    public LFUCache(int capacity) {

        this.capacity = capacity;

        this.size = 0;

        this.head = new DLinkNode(0);

        this.tail = new DLinkNode(Integer.MAX_VALUE);

        this.head.next = this.tail;

        this.tail.prev = this.head;

        this.map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        KV kv = map.get(key);

        selfIncre(kv);

        return map.get(key).value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {

            KV kvElement = map.get(key);

            selfIncre(kvElement);

            kvElement.value = value;

        }
        else {

            if (size >= capacity) {
                removeLess();
            }
            if (size < capacity) {
                size++;

                KV kvElement = new KV(head,value,key);

                head.linkKV(kvElement);

                map.put(key,kvElement);

                selfIncre(kvElement);
            }
        }
    }

    private void selfIncre (KV kvElement) {

        DLinkNode currentDLinkNode = kvElement.currentPosition;

        currentDLinkNode.unLinkKV(kvElement);

        DLinkNode shouldNext = currentDLinkNode.next;

        if (needLinkNewNode(currentDLinkNode.count,currentDLinkNode.next == null ? Integer.MAX_VALUE : currentDLinkNode.next.count)) {

            shouldNext = createNewDLinkNodeWithFristKV(currentDLinkNode.count + 1,kvElement);

            linkDNode(shouldNext,currentDLinkNode);
        }
        else {
            shouldNext.linkKV(kvElement);
        }

        if (currentDLinkNode != head && needRemoveNode(currentDLinkNode)) {
            unlinkDNode(currentDLinkNode);
        }
    }

    /**
     * 删除使用次数最少的节点
     */
    private void removeLess () {

        if (size == 0) return;

        DLinkNode minDLinkNode = head.next;

        KV minKVElement = minDLinkNode.head.next;

        minDLinkNode.unLinkKV(minKVElement);

        this.map.remove(minKVElement.key);

        this.size--;

        if (needRemoveNode(minDLinkNode)) {
            unlinkDNode(minDLinkNode);
        }
    }

    private boolean needLinkNewNode (int current,int next) {
        return next - current > 1;
    }

    private boolean needRemoveNode (DLinkNode node) {
        return node.tail == null;
    }

    /**
     * 增加一个新的节点到双向链表
     * @param node
     */
    private void linkDNode (DLinkNode node,DLinkNode last) {

        if (node == null) return;

        if (tail == null) {
            tail = node;
        }

        node.next = last.next;

        node.prev = last;

        if (last.next != null) {
            last.next.prev = node;
        }

        last.next = node;
    }

    /**
     * 从 DLinkNode 双向链表中删除这个节点
     * 可能删除的节点会是尾节点
     * @param node
     */
    private void unlinkDNode (DLinkNode node) {

        if (node == null || node.tail != null) return;

        node.prev.next = node.next;

        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    private DLinkNode createNewDLinkNodeWithFristKV (int count,KV fristKV) {

        DLinkNode node = new DLinkNode(count);

        node.linkKV(fristKV);

        return node;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        //        stringBuilder.append("max.s-e{");
        //        DLinkNode start = head;
        //        while (start != null) {
        //            stringBuilder.append(start);
        //            start = start.next;
        //        }
        //        DLinkNode end = tail;
        //        stringBuilder.append("}\nmax.e-s{");
        //        while (end != null) {
        //            stringBuilder.append(end);
        //            end = end.prev;
        //        }
        //        stringBuilder.append("}");
        //
        //        stringBuilder.append("LFUCache{" +
        //                "size=" + size +
        //                ", capacity=" + capacity +
        //                ", map=" + map +
        //                "}\n");

        Set<Integer> keySet = map.keySet();
        Iterator<Integer> ite = keySet.iterator();
        while (ite.hasNext()) {
            Integer keyItem = ite.next();
            stringBuilder.append(String.format("(key=%d、value=%d、count=%d),",keyItem,map.get(keyItem).value,map.get(keyItem).currentPosition.count));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}