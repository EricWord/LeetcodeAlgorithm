package net.codeshow;

import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author eric
 * @Version V1.0.0
 * @Date 2021/5/2
 */
class TrafficLight {

    public TrafficLight() {

    }

    private boolean road1IsGreen = true;
    private Semaphore singal = new Semaphore(1);

    public void carArrived(
            int carId,           // ID of the car
            int roadId,          // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
            int direction,       // Direction of the car
            Runnable turnGreen,  // Use turnGreen.run() to turn light to green on current road
            Runnable crossCar    // Use crossCar.run() to make car cross the intersection
    ) {
        try {
            singal.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ((roadId == 1) != road1IsGreen) {
            turnGreen.run();
            road1IsGreen = !road1IsGreen;
        }
        crossCar.run();
        singal.release();

    }
}

