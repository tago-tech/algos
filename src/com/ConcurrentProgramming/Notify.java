package com.ConcurrentProgramming;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Notify extends Thread {
    //类共享int值
    static int val = 0;
    static int max = 20;
    //线程编号
    int threadNo;
    //总线程数
    static int threadsCount = 0;
    static ReentrantLock reentrantLock = new ReentrantLock(true);
    static Condition c1 = reentrantLock.newCondition();
    public Notify(int threadNo,String name) {
        super(name);
        this.threadNo = threadNo;
        threadsCount++;
    }
    public void vf() throws InterruptedException {

    }
    @Override
    public void run() {

        while (true && val <= max) {
            reentrantLock.lock();
            try {
                while (this.threadNo != (val % threadsCount)) {
                    if (val > max) {
                        break;
                    }
                    c1.await();
                }
                if (val <= max) {
                    System.out.println(Thread.currentThread().getName() + " : " + (val++));
                }
                //System.out.println(Thread.currentThread().getName() + " 被唤醒");
                c1.signalAll();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                reentrantLock.unlock();
            }
        }
    }
    public static void main (String[] args) throws InterruptedException{
        int N = 9;
        Notify[] ns = new Notify[N];
        for (int i = 0;i < N;i++) {
            ns[i] = new Notify(i,"t" + i);
            ns[i].start();
        }
        for (int i = 0;i < N;i++) {
            ns[i].join();
        }
        System.out.println("运行结束");
    }
}
