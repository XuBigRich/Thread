package test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 许鸿志
 * @since 2021/7/26
 */
public class ExceptionDemo {
    public static class ResultThread implements Callable<Integer> {

        @Override
        public Integer call() {
            return 0 / 0;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ResultThread resultThread = new ResultThread();
        FutureTask<Integer> futureTask = new FutureTask<>(resultThread);
        Thread thread = new Thread(futureTask);
        thread.start();
        futureTask.get();

    }
}