class Counter {
    private final Object lock = new Object();
    private boolean countingUp = true;

    public void countUp() {
        synchronized (lock) {
            for (int i = 1; i <= 20; i++) {
                System.out.println("Thread 1 counting up: " + i);
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            countingUp = false;
            lock.notify(); // Notify the second thread
        }
    }

    public void countDown() {
        synchronized (lock) {
            while (countingUp) {
                try {
                    lock.wait(); // Wait until counting up is complete
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            for (int i = 20; i >= 0; i--) {
                System.out.println("Thread 2 counting down: " + i);
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

public class Concurrency {
    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Thread thread1 = new Thread(counter::countUp);
        Thread thread2 = new Thread(counter::countDown);
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}