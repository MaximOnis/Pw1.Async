import java.util.concurrent.Semaphore;
import java.time.LocalTime;
import java.util.Random;


class Vehicle implements Runnable{
    private final Semaphore sem;
    private final Random rand;
    private final String name;

    Vehicle(Semaphore sem, String name, Random rand) {
        this.sem = sem;
        this.name = name;
        this.rand = rand;
    }

    @Override
    public void run()
    {
        try
        {
            // Машина заїжджає
            sem.acquire();
            System.out.println ("Автомобіль " + name + " займає місце");

            // Стоїть....
            Thread.sleep(rand.nextInt(5000));

            System.out.println ("Автомобіль " + name + " покидає місце");
            sem.release();

            // Машина Виїжджає
            Thread.sleep(500);
        }
        catch(InterruptedException e) {
            System.out.println ("Щось пішло не так!");
        }
    }
}


public class Main {
    public static void main(String[] args) {
        // Поточний час
        LocalTime now = LocalTime.now();
        Semaphore sem;
        // Встановлення режиму роботи
        if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(21, 0))) {
            sem = new Semaphore(5);
        } else {
            sem = new Semaphore(8);
        }
        startParking(sem);

    }

    public static void startParking(Semaphore sem){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            String name = Integer.toString(i);
            new Thread(new Vehicle(sem, name, rand)).start();
        }
    }
}