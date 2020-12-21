package com.datastructure;

import java.util.*;

/**
 * SkipList-0.9 Impl
 * author by tangsz.
 * Road-map:
 *  a.genericity ability;
 *  b.concurrent ability;
 * when 2020.12.
 */
public class SkipList {

    static final int SKIP_LIST_MAX_LEVEL = 64;

    static final double SKIP_LIST_P = 0.25;

    private Random random;

    private long length;

    private int level;

    private SkipListNode head , tail;

    static class SkipListNode {

        String ele;

        double score;

        SkipListNode backward;

        SkipListLevel[] level;

        public SkipListNode() {
        }

        public SkipListNode(String ele, double score) {
            this.ele = ele;
            this.score = score;
        }

        public SkipListNode(String ele, double score, int levelC) {
            this.ele = ele;
            this.score = score;
            this.level = new SkipListLevel[levelC];
            for (int i = 0;i < levelC;i++) {
                level[i] = new SkipListLevel();
            }
        }

        @Override
        public String toString() {

            StringBuilder stringBuilder = new StringBuilder(String.format("(ele=%4s,score=%3d)",ele,(int)score));
            int i = 0;
            while (i < level.length && level[i] != null) {
                stringBuilder.append(String.format("[*%3d]",level[i].span));
                i++;
            }
            return stringBuilder.toString();
        }
    };

    static class SkipListLevel {

        SkipListNode forward;

        long span;
    };

    //construct method
    public SkipList() {

        random = new Random();

        head = sCreateHeadNode();

        head.backward = null;

        level = 1;

        length = 0;

        tail = null;
    }

    private SkipListNode sCreateHeadNode () {
        SkipListNode node = new SkipListNode(null,Double.MIN_VALUE,SKIP_LIST_MAX_LEVEL);
        for (int i = 0 ; i < SKIP_LIST_MAX_LEVEL ; i++) {
            node.level[i].forward = null;
            node.level[i].span = 0;
        }
        return node;
    }


    /**
     * insert ele into skiplist.
     * a.find the right postion;
     * b.adjust skiplist height;
     * c.insert element;
     * d.adjust backward and forward;
     *  update[] : previuse node in each level.
     *  rank[] : the step distance between head node and update[i].
     * @param ele
     * @param score
     *
     */
    public void insert (String ele , double score) {

        SkipListNode x = head;

        int[] rank = new int[SKIP_LIST_MAX_LEVEL];

        SkipListNode[] update = new SkipListNode[SKIP_LIST_MAX_LEVEL];

        for (int i = level - 1 ; i >= 0 ; i--) {

            rank[i] = i == level - 1 ? 0 : rank[i + 1];

            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score || (x.level[i].forward.score == score && x.level[i].forward.ele.compareTo(ele) < 0))
            ) {

                rank[i] += x.level[i].span;

                x = x.level[i].forward;
            }
            update[i] = x;
        }

        int eleLevel = getSkipListRandomLevel();

        for (int i = level ; i < eleLevel ; i++) {

            rank[i] = 0;

            update[i] = head;

            update[i].level[i].span = length;
        }

        level = Math.max(level,eleLevel);

        //create current node
        SkipListNode element = new SkipListNode(ele,score,eleLevel);

        //adjust span and forward.
        for (int i = 0 ; i < eleLevel ; i++) {

            element.level[i].forward = update[i].level[i].forward;

            update[i].level[i].forward = element;

            element.level[i].span = update[i].level[i].span - (rank[0] - rank[i]);

            update[i].level[i].span = (rank[0] - rank[i]) + 1;
        }

        for (int i = eleLevel ; i < level ; i++) {
            update[i].level[i].span++;
        }

        //adjust backward.
        element.backward = (update[0] == head) ? null : update[0];

        if (element.level[0].forward != null) {
            element.level[0].forward.backward = element;
        }
        else
            tail = element;

        length++;
    }

    /**
     * delete node from skiplist.
     * a.find node 's postion;
     * b.set span and forward;
     * @param ele
     * @param score
     */
    public SkipListNode delete (String ele,double score) throws RuntimeException{

        if (length <= 0) throw  new RuntimeException("length must bigger than zero.");

        SkipListNode x = head;

        int[] rank = new int[SKIP_LIST_MAX_LEVEL];

        SkipListNode[] update = new SkipListNode[SKIP_LIST_MAX_LEVEL];

        for (int i = level - 1 ; i >= 0 ; i--) {

            rank[i] = i == level - 1 ? 0 : rank[i + 1];

            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score || (x.level[i].forward.score == score && x.level[i].forward.ele.compareTo(ele) < 0))
            ) {

                rank[i] += x.level[i].span;

                x = x.level[i].forward;
            }

            update[i] = x;
        }

        //move to next element
        x = x.level[0].forward;

        // no ele exists.
        if (x == null || !x.ele.equals(ele) || x.score != score) {
            throw new RuntimeException(String.format("no (ele=" + ele + ",score=" + score +") element exist in skip-list."));
        }

        //adjust span and forward
        for (int i = 0;i < level;i++){
            if (update[i].level[i].forward == x) {

                update[i].level[i].span += x.level[i].span - 1;

                update[i].level[i].forward = x.level[i].forward;

            }
            else {
                update[i].level[i].span -= 1;
            }
        }

        //adjust backward
        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x.backward;
        }
        else {
            tail = x.backward;
        }

        //remove empty level.
        while (level > 1 && head.level[level - 1].forward == null) {
            level--;
        }

        length--;

        return x;
    }

    private int getSkipListRandomLevel () {
        int level = 1;
        while (random.nextDouble() < SKIP_LIST_P) {
            level++;
        }
        return level < SKIP_LIST_MAX_LEVEL ? level : SKIP_LIST_MAX_LEVEL;
    }

    public long size() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{\n");
        SkipListNode x = head;
        while (x != null) {
            stringBuilder.append(x.toString() + "\n");
            x = x.level[0].forward;
        }
        stringBuilder.append(String.format("length=%d,level=%d}",length,level));
        return stringBuilder.toString();
    }

    /**
     * functional test.
     * we take random int number insert into Skip-List and random chiose element delete.
     * @param args
     * @throws RuntimeException
     */
    public static void main(String[] args) throws RuntimeException{
        SkipList sl = new SkipList();

        Random random = new Random();

        List<Integer> set = new ArrayList<>(100);

        int genCount = 10;

        int removeCount = 3;

        while (genCount > 0) {
            String ele = String.valueOf(random.nextInt(100));
            set.add(Integer.valueOf(ele));
            System.out.println("Generate element = " + ele);
            sl.insert(ele,Integer.valueOf(ele));
            System.out.println("Skip-List=\n" + sl.toString());
            genCount--;
        }

        System.out.println("Current Skip-List=\n" + sl.toString());
        while (removeCount > 0) {
            String ele = String.valueOf(set.get(random.nextInt(set.size() - 1)));
            SkipListNode removedNode = sl.delete(ele,Integer.valueOf(ele));
            System.out.println(String.format("execpet:%s,removed:%s;skip-list=\n%s",ele,removedNode,sl));
            removeCount--;
        }
    }
}

