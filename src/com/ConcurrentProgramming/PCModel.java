package com.ConcurrentProgramming;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用线程池实现消费者生产者模型
 * 利用阻塞队列,Lock + Conidition
 * */
public class PCModel {

    public static void main(String[] args) throws InterruptedException {
        //阻塞队列
        BQ bq = new BQ(3);
        int messagesNum = 120,produceNum = 3,consumerNum = 2;
        //消费者线程池
        ExecutorService consumerExecutorService = Executors.newFixedThreadPool(consumerNum);
        for (int i = 0;i < consumerNum;i++) {
            consumerExecutorService.submit(new Consumer2(bq,messagesNum / consumerNum));
        }
        //生产者线程池
        ExecutorService produceExecService = Executors.newFixedThreadPool(produceNum);
        for (int i = 0;i < produceNum;i++) {
            produceExecService.submit(new Produce2(bq,messagesNum / produceNum));
        }
        produceExecService.shutdown();
        consumerExecutorService.shutdown();

        System.out.println("主线程运行结束");
    }
}
//共享对象
class BQ {
    //控制生产者消费者模型的阻塞队列
    int[] table;
    int MAXLIMIT;
    int curSize;
    static Lock lock = new ReentrantLock();
    static Condition empty = lock.newCondition();
    static Condition full = lock.newCondition();
    public BQ(int MAX) {
        this.curSize = -1;
        this.table = new int[MAX];
        this.MAXLIMIT = MAX;
    }
    public int get() {
        int x = -1;
        try {
            lock.lock();
            while (curSize == -1) {
                empty.await();
            }
            x = this.table[curSize--];
            System.out.println(Thread.currentThread().getName() + " Consumer " + x);
            Thread.sleep(10000);
            full.signal();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
        return x;
    }
    public synchronized void put(int x) {
        try {
            lock.lock();
            while (curSize == MAXLIMIT - 1) {
                full.await();
            }
            System.out.println(Thread.currentThread().getName() + " Produce " + x);
            this.table[++curSize] = x;
            Thread.sleep(5000);
            empty.signal();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
//消费者
class Consumer2 implements Runnable {
    //消费的对象
    BQ bq;
    //消费次数
    int limit;
    public Consumer2(BQ bq,int limit) {
        this.bq = bq;
        this.limit = limit;
    }
    @Override
    public void run() {
        int idx = 0;
        while (idx++ < limit) {
            bq.get();
        }
    }
}
//生产者
class Produce2 implements Runnable {
    //消费的对象
    BQ bq;
    //消费次数
    int limit;
    //生产方法
    static Random random = new Random(666);
    public Produce2(BQ bq,int limit) {
        this.limit = limit;
        this.bq = bq;
    }
    @Override
    public void run() {
        int idx = 0;
        while (idx++ < limit) {
            bq.put(random.nextInt());
        }
    }
}

