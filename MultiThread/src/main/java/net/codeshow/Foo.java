package net.codeshow;

/**
 * 方法1：使用Semaphore
 * 方法2：volatile+计数变量
 * 方法3：使用AtomicInteger
 * 方法4：synchronized+wait+notifyAll
 */
class Foo {

    public Foo() {

    }

    private boolean firstdDone;
    private boolean secondDone;
    private final Object object = new Object();

    public void first(Runnable printFirst) throws InterruptedException {

        synchronized (object) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstdDone = true;
            object.notifyAll();

        }
    }

    public void second(Runnable printSecond) throws InterruptedException {

        synchronized (object) {
            while (!firstdDone) {
                object.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondDone = true;
            object.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        synchronized (object) {
            while (!secondDone) {
                object.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
}
