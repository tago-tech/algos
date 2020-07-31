package com.ConcurrentProgramming;

import org.omg.CORBA.INITIALIZE;

import java.util.Random;

public class CPModelObject {
    public static void main(String[] args) throws InterruptedException {
        int messagesNum = 6,produceNum = 1,consumerNum = 2;
        Qu q = new Qu(2);
        Cs[] css = new Cs[consumerNum];
        Pd[] pds = new Pd[produceNum];
        for (int i = 0;i < produceNum;i++) {
            pds[i] = new Pd(q,"p" + i,messagesNum/produceNum);
            pds[i].start();
        }
        for (int i = 0;i < consumerNum;i++) {
            css[i] = new Cs(q,"c" + i,messagesNum / consumerNum);
            css[i].start();
        }
        for (int i = 0;i < produceNum;i++) {
            pds[i].join();
        }
        for (int i = 0;i < consumerNum;i++) {
            css[i].join();
        }
        System.out.println("主线程运行结束");
    }
}
//中间件
class Qu {
    int[] table;
    int size;
    int Max;
    public Qu(int Max) {
        this.table = new int[Max];
        this.Max = Max;
        this.size = -1;
    }
    public int get() {
        return table[size--];
    }
    public void put(int x) {
        this.table[++size] = x;
    }
    public int getSize() {
        return this.size;
    }
    public int getMax() {
        return this.Max;
    }
}
//生产者
class Pd extends Thread {
    Qu q;
    int limit;
    static Random random = new Random(97);
    public Pd(Qu q,String name,int limit) {
        super(name);
        this.q = q;
        this.limit = limit;
    }
    @Override
    public void run() {
        for (int i = 0;i < limit;i++) {
            synchronized (q) {
                try {
                    while (q.getSize() == q.getMax() - 1) {
                        q.wait();
                    }
                    int x = random.nextInt();
                    q.put(x);
                    System.out.println(Thread.currentThread().getName() + " Produce : " + x);
                    q.notify();
                }
                catch (InterruptedException e) {

                }
                finally {

                }
            }
        }
    }
}
//消费者
class Cs extends Thread {
    Qu q;
    int limit;
    public Cs(Qu q,String name,int limit) {
        super(name);
        this.q = q;
        this.limit = limit;
    }
    @Override
    public void run() {
        for (int i = 0;i < limit;i++) {
            synchronized (q) {
                try {
                    while (q.getSize() == -1) {
                        q.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + " Consum : " + q.get());
                    q.notify();
                }
                catch (InterruptedException e) {

                }
                finally {

                }
            }
        }
    }
}