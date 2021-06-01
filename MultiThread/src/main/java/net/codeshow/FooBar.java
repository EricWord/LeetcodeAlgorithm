package net.codeshow;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description
 * @Author eric
 * @Version V1.0.0
 * @Date 2021/5/1
 */

class FooBar {
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    private CyclicBarrier barrier = new CyclicBarrier(2);
    volatile boolean fin = true;

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!fin) ;
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            fin = false;
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            fin = true;
        }
    }
}