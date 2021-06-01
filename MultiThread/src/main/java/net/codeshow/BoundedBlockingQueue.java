package net.codeshow;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBlockingQueue {
    private volatile int cap;
    private AtomicInteger size = new AtomicInteger(0);
    private LinkedList<Integer> container;

    public BoundedBlockingQueue(int capacity) {
        this.cap = capacity;
        container = new LinkedList<>();

    }

    private static Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while (size.get() >= cap) {
                producer.await();
            }
            container.addFirst(element);
            size.incrementAndGet();
            consumer.signal();
        } finally {
            lock.unlock();
        }

    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (size.get() == 0) {
                consumer.await();
            }
            Integer last = container.getLast();
            container.removeLast();
            size.decrementAndGet();
            producer.signal();
            return last;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return size.get();
        } finally {
            lock.unlock();
        }
    }
}