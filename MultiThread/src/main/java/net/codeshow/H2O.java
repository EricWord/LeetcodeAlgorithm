package net.codeshow;

import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author eric
 * @Version V1.0.0
 * @Date 2021/5/2
 */
class H2O {

    public H2O() {

    }

    private Semaphore H = new Semaphore(2);
    private Semaphore O = new Semaphore(0);
    private volatile int counter = 0;

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        H.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        counter++;
        if (counter == 2) {
            O.release();
            counter = 0;
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        O.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        H.release();
        H.release();
    }
}
