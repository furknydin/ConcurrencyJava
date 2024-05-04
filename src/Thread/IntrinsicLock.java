package Thread;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class IntrinsicLock {
    public static void main(String[] args) {
        IntrinsicLock demo = new IntrinsicLock();
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 before call "+ LocalDateTime.now());
            demo.syncStatement("from thread1 ");
            System.out.println("thread1 after call "+LocalDateTime.now());
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 before call "+LocalDateTime.now());
            demo.syncStatement("from thread2");
            System.out.println("thread2 after call "+LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
    }

    private synchronized void syncMethod(String msg){
        System.out.println("in the sync method "+msg+" "+ LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void syncStatement(String msg){
        System.out.println("in the sync method "+msg+" "+ LocalDateTime.now());
        synchronized (this){
            System.out.println("in the sync statement "+msg+" "+ LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
