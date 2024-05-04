package AsynchJava;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTask {


    private Integer[] numbers = {1, 2, 3, 4, 5};
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTask task = new CompletableFutureTask();
        System.out.println(task.sumOfSquareNumbers());
    }

    private Integer sumOfSquareNumbers() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer>[] taskSquare = Arrays.stream(numbers)
                                .map(numbers -> CompletableFuture.supplyAsync(() ->numbers*numbers))
                                .toArray(CompletableFuture[]::new);

        CompletableFuture<Integer> taskSum = CompletableFuture.allOf(taskSquare)
                .thenApply(v -> Arrays.stream(taskSquare)
                        .map(CompletableFuture::join)
                        .reduce(0, Integer::sum));

        return taskSum.get();
    }
}
