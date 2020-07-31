package com.ConcurrentProgramming;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.Semaphore;

public class Main {
    public static synchronized void tenst() {
        try {
            Thread.sleep(8000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {

        }
    }
    //使用通知等待机制实现交替打印 1-100
    public static void main(String[] args) throws InterruptedException{
        //偶数线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Main.tenst();
                }
            }
        },"ji");
        //奇数线程
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Main.tenst();
                }
            }
        },"ou");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
