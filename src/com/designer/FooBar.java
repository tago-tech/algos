package com.designer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 交替打印FooBar
 *
 * 存在问题
 *  1.两个线程启动时间可能不同;
 *  2.运行过程中两个线程可能连续抢到锁,公平锁　或者　分离锁设计;
 */
public class FooBar {

    private int n;

    //两个信号量分别控制两个线程的执行顺序,设置初始值不同
    private Semaphore semaphore1;

    private Semaphore semaphore2;

    //闭锁　协调两个线程同时达到临界处
    private CountDownLatch countDownLatch;

    public FooBar(int n) {

        this.n = n;

        this.semaphore1 = new Semaphore(0,true);

        this.semaphore2 = new Semaphore(1,true);

        this.countDownLatch = new CountDownLatch(2);

    }

    public void foo(Runnable printFoo) throws InterruptedException {

        countDownLatch.countDown();

        for (int i = 0; i < n; i++) {

            semaphore2.acquire();

            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();

            semaphore1.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        countDownLatch.countDown();

        for (int i = 0; i < n; i++) {

            semaphore1.acquire();

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();

            semaphore2.release();
        }
    }
}
