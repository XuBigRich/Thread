package test.exception.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程相关的异常问题
 * 问题：
 * 主线程开启的线程，子线程 报错 其实并不会被主线程捕获到。需要使用get方式 建立关系，才可以被捕获到
 * 主线程如何捕获分支线程的异常
 *
 * @author 许鸿志
 * @since 2021/7/26
 */
public class ExceptionDemo {
    public static class ResultThread implements Callable<Integer> {
        Thread thread;

        public ResultThread() {
        }

        public ResultThread(Thread thread) {
            this.thread = thread;
        }


        @Override
        public Integer call() throws InterruptedException {
            //如果要插队线程不为null ，那就执行插队 命令
            if (thread != null) {
                this.thread.join();
            }
            System.out.println("我先执行");
            Thread.sleep(2000);
            //这个异常 只有在主线程调用get 的时候才可以被主线程捕获到，否则 即使报错了 主线程也不会被感知也不会抛出
            return 1 / 0;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ResultThread resultThread = new ResultThread();
        FutureTask<Integer> futureTask = new FutureTask<>(resultThread);
        Thread thread = new Thread(futureTask);
        thread.start();
        //等待子线程执行完毕，让子线程插队
        thread.join();
        System.out.println("我先执行2");
//        System.out.println(futureTask.get());

    }
}
