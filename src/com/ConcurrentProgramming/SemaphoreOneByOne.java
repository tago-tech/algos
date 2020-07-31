package com.ConcurrentProgramming;

import java.util.concurrent.Semaphore;

public class SemaphoreOneByOne {
    static volatile int val = 1;
    public static void main(String[] args) throws InterruptedException {
        //N个线程交替打印 1 - 100
        int N = 2;
        int Max = 10;
        SemaphoreOneByOne s = new SemaphoreOneByOne();
        Thread[] threadsCollections = new Thread[3];
        //声明信号量
        Semaphore[] semaphores = new Semaphore[3];
        for (int i = 0;i < N;i++) {
            semaphores[i] = new Semaphore(1);
            if (i != N -1) {
                semaphores[i].acquire();
            }
        }
        for (int i = 0;i < N;i++) {
            Semaphore laseSemaphore = i == 0 ? semaphores[N - 1] : semaphores[i - 1];
            Semaphore curSemaphore = semaphores[i];
            threadsCollections[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (val <= Max) {
                            //等待上一个信号量
                            laseSemaphore.acquire();
                            if (val <= Max) {
                                System.out.println(Thread.currentThread().getName() + " : " + val++);
                            }
                            curSemaphore.release();
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"thread-" + i);
            threadsCollections[i].start();
        }
        for (int i = 0;i < N;i++) {
            threadsCollections[i].join();
        }
        System.out.println("运行结束");
    }
}
