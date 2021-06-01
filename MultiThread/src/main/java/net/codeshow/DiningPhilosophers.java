package net.codeshow;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author eric
 * @Version V1.0.0
 * @Date 2021/5/2
 */
class DiningPhilosophers {
    private Lock[] lockList = new ReentrantLock[]{
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    };
    private Semaphore eatLimit = new Semaphore(4);

    public DiningPhilosophers() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int leftFork=(philosopher+1)%5;
        int rightFork=philosopher;
        eatLimit.acquire();
        lockList[leftFork].lock();
        lockList[rightFork].lock();
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        lockList[leftFork].unlock();
        lockList[rightFork].unlock();
        eatLimit.release();

    }
}