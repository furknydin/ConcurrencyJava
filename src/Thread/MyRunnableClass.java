package Thread;

import javax.annotation.concurrent.ThreadSafe;

public class MyRunnableClass implements Runnable{
    private boolean doStop = false;

    public synchronized void doStop(){
        this.doStop = true;
    }

    private synchronized boolean keepRunning(){
        return this.doStop == false;
    }
    @Override
    public void run() {
        while(keepRunning()) {
            System.out.println("Running");

            try {
                Thread.sleep(3L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        MyRunnableClass myRunnable = new MyRunnableClass();

        Thread thread = new Thread(myRunnable);

        thread.start();

        try {
            Thread.sleep(10L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myRunnable.doStop();

    }
}
