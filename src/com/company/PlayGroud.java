package com.company;

import java.util.concurrent.Semaphore;

public class PlayGroud {
    static int result = 0;
    public static void main(String[] args) throws InterruptedException {
        int threadNum = 12;
        int maxInt = 100;
        Semaphore[] semaphores = new Semaphore[threadNum];
        for (int i = 0;i < threadNum;i++) {
            semaphores[i] = new Semaphore(1);
            if (i != threadNum - 1) {
                semaphores[i].acquire();
            }
        }
        for (int i = 0;i < threadNum;i++) {
            final Semaphore lastSem = i == 0 ? semaphores[threadNum - 1]: semaphores[i - 1];
            final Semaphore curSem = semaphores[i];
            final int index = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            lastSem.acquire();
                            System.out.println("Thread-" + index + " : " + result++);
                            if (result > maxInt) {
                                System.exit(1);
                            }
                            curSem.release();
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
        }
    }
}
