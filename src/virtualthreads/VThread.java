package virtualthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.Duration;
import java.util.stream.IntStream;

public class VThread {

    private static final Logger logger = Logger.getLogger("Thread Log");


    public static void main(String[] args) {

        multipleVirtualThread();

    }

    public static void singleVirtualThread(){
        try{
            Thread thread = Thread.ofVirtual().start(() -> logger.info("Hello"));
            thread.join();
        }catch (InterruptedException e){
            logger.log(Level.WARNING,"Thread is interrupted",e);
            Thread.currentThread().interrupt();
        }
    }

    public static void doubleVirtualThread(){
        try{
            Thread.Builder builder = Thread.ofVirtual().name("worker-",0);
            Thread t1 = builder.start(task());
            t1.join();
            logger.info(t1.getName() + " terminated");
            Thread t2 =builder.start(task());
            t2.join();
            logger.info(t2.getName() + " terminated");



        }catch (InterruptedException e){
            logger.log(Level.WARNING,"Thread is interrupted",e);
            Thread.currentThread().interrupt();
        }
    }

    public static void executorVirtualThread(){

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        try{
            Future<?> future = executorService.submit(() -> logger.info("executor service is running"));
            future.get();
            logger.info("Task completed");
        }catch (ExecutionException | InterruptedException e) {
            logger.log(Level.WARNING, "Thread is interrupted", e);
            Thread.currentThread().interrupt();
        }finally {
            executorService.close();
        }
    }

    public static void multipleVirtualThread(){
        List<Future<Integer>> futures = new ArrayList<>();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 170_000).forEach(i -> {
                Future<Integer> future = executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1).toMillis());
                    return i;
                });
                futures.add(future);
            });

            futures.forEach(future -> {
                try {
                    Integer result = future.get();
                    logger.info("Result: "+result);
                } catch (InterruptedException | ExecutionException e) {
                    logger.log(Level.WARNING, "Thread is interrupted", e);
                    Thread.currentThread().interrupt();
                }
            });
        }


    }

    private static Runnable task(){
        return () -> logger.info("Thread ID: " + Thread.currentThread().threadId());

    }

}
