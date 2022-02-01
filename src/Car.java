public class Car extends Thread {
    private final int speed;
    private final int road;
    private final int capacity;



    Car(int speed, int road, int capacity) {
        this.speed = speed;
        this.road = road;
        this.capacity = capacity;
    }

    protected void move() {
        long time = (road / speed) * 3000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadReload() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            loadReload();
            move();
            move();
        }
    }

    public int getCapacity() {
        return capacity;
    }
}