package com.company;

import java.util.Arrays;

public class MyHeap {
    private int[] nums;
    private int cap;
    public MyHeap() {
        this.cap = 0;
        this.nums = new int[1];
    }
    public boolean isEmpty () {
        return this.cap == 0;
    }
    public int getMin() {
        return this.nums[1];
    }
    public int deleteMin() {
        if (isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int rs = getMin();
        this.nums[1] = this.nums[cap--];
        parseDown(1);
        return rs;
    }
    public void insert(int x) {
        //扩容
        this.cap++;
        if (this.nums.length <= cap) {
            this.nums = Arrays.copyOf(this.nums,cap  * 2);
        }
        this.nums[cap] = x;
        parseUp(cap);
    }
    public void buildHeap(int[] arrays) {
        this.nums = new int[arrays.length + 1];
        for (int i = 0 ; i < arrays.length ; i++) {
            this.nums[i + 1] = arrays[i];
        }
        this.cap = this.nums.length - 1;
        //找出最小的非叶节点
        for (int i = this.cap / 2;i > 0;i--) {
            parseDown(i);
        }
    }
    private void parseDown(int root) {
        System.out.println("root = "+ root);
        this.nums[0] = this.nums[root];
        int minChild = root * 2;
        while (minChild + 1 <= cap || minChild <= cap) {

            if (minChild + 1 <= cap && this.nums[minChild] > this.nums[minChild + 1]) {
                minChild++;
            }
            if (this.nums[minChild] < this.nums[0]) {
                //swap
                this.nums[root] = this.nums[minChild];
            }
            else {
                break;
            }
            root = minChild;
            minChild = root * 2;
        }
        this.nums[root] = this.nums[0];
    }

    private void parseUp(int root) {
        this.nums[0] = this.nums[root];
        while (this.nums[root / 2] > this.nums[0]) {
            this.nums[root] = this.nums[root / 2];
            root = root / 2;
        }
        this.nums[root] = this.nums[0];
    }
    public void printHeap() {
        for (int i = 1;i <= cap;i++) {
            System.out.print(", "+this.nums[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] nums = {10,9,8,7,6,5};
        MyHeap myHeap = new MyHeap();
        myHeap.buildHeap(nums);
        myHeap.printHeap();
        myHeap.getMin();
        myHeap.deleteMin();
        myHeap.printHeap();
        myHeap.insert(1);
        myHeap.printHeap();
    }
}
