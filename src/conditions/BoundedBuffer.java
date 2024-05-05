package conditions;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class BoundedBuffer<E> {
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private final Object[] items;
    private int putPtr, takePtr, count;


    public BoundedBuffer() {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        items = new Object[100];
    }

    public void put(E x) {
        lock.lock();

        try{
            while (count == items.length)
                notFull.await();
            items[putPtr] = x;
            if (++putPtr == items.length){
                putPtr = 0;
            }
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e){
            System.out.println("Interrupted Exception handle");
        } finally {
            lock.unlock();
        }

    }

    public E take(){
        lock.lock();

        try{
            while (count == 0){
                notEmpty.await();
            }
            @SuppressWarnings("unchecked")
            E x = (E) items[takePtr];

            if (++takePtr == items.length){
                takePtr = 0;
            }

            --count;
            notFull.signal();
            return x;
        } catch (InterruptedException e){
            System.out.println("Interrupted Exception handle");
            return (E) new Object();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedBuffer<String> test = new BoundedBuffer<>();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(() -> {
                int i = 0;
                while (i < 100){
                    String uuid = UUID.randomUUID().toString();
                    System.out.println("Put Id: " + uuid);
                    test.put(uuid);
                    i++;
                }
            });
            executor.execute(() -> {
                int i = 0;
                while (i < 100) {
                    String take = test.take();
                    System.out.println("Take Id: " + take);
                    i++;
                }
            });
        }

    }


}
