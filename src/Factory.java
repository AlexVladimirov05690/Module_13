import java.util.concurrent.atomic.AtomicInteger;

public class Factory extends Thread {

    private AtomicInteger storageApple = new AtomicInteger(0);
    private AtomicInteger storageJucie = new AtomicInteger(0);
    final long TIME_WORK = 30000;
    final long TIME_START;

    final private int ROAD_TO_FARM;
    final private int ROAD_TO_CITY;


    public Factory(int ROAD_TO_FARM, int ROAD_TO_CITY) {
        TIME_START = System.currentTimeMillis();
        this.ROAD_TO_FARM = ROAD_TO_FARM;
        this.ROAD_TO_CITY = ROAD_TO_CITY;
        new CarIn(10,ROAD_TO_FARM, 2).start();
        new CarOut(5, ROAD_TO_CITY, 6).start();

    }


    @Override
    public void run() {
        while (((System.currentTimeMillis() - TIME_START) < TIME_WORK) || storageApple.get() < 4)
        {
            System.out.println("Времени от начала программы: " + (System.currentTimeMillis() - TIME_START)/1000 + " c.");
            if (storageApple.get() >= 4) {
                try {
                    System.out.println("Производим сок");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                storageApple.set(storageApple.get() - 4);
                storageJucie.getAndIncrement();
                System.out.println("Яблок: " + storageApple.get());
                System.out.println("Сока: " + storageJucie.get());
            } else {
                System.out.println("На складе не хватает продукции");
                System.out.println("Яблок: " + storageApple.get());
                System.out.println("Сока: " + storageJucie.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
        System.out.println("Работа фабрики остановлена из-за недостатка сырья");
    }
    class CarIn extends Car {

        CarIn(int speed, int road, int capacity) {
            super(speed, road, capacity);
        }

        void reload() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storageApple.getAndAdd(super.getCapacity());

        }

        @Override
        public void run() {
            while ((System.currentTimeMillis() - TIME_START) < TIME_WORK) {
                super.move();
                reload();
                super.move();
            }
            System.out.println("Рабочий день поставщика закончен");
            Thread.interrupted();


        }

    }
    class CarOut extends Car {

        CarOut(int speed, int road, int capacity) {
            super(speed, road, capacity);
        }

        void load() {
            System.out.println("Идёт зaгрузка...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (storageJucie.get() <= super.getCapacity()){
            }
            storageJucie.set(storageJucie.get() - super.getCapacity());
        }

        @Override
        public void run() {
            while (System.currentTimeMillis() - TIME_START < TIME_WORK) {
                super.move();
                load();
                super.move();
            }
            System.out.println("Рабочий день перевозчика закончен");
            Thread.interrupted();
        }
    }
}
