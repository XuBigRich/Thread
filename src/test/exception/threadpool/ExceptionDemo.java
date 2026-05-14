package test.exception.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 关于异常有一个问题，就是子线程的异常 通常 无法直接传导给主线程 （除非使用get获取结果） 下面是解决方案
 *
 * 关于线程池如何获取异常的 示例
 * 如果是一组线程，判断这组线程中 是否全部成功，可以用下面的方法去判断 这些线程中有没有异常情况
 * 使用Future
 *
 * @author 许鸿志
 * @since 2022/8/19
 */
public class ExceptionDemo {
    static ExecutorService threadPool = ThreadPoolUtils.getThreadPool();


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ResultThread resultThread = new ResultThread();
        int i = 0;
        List<Future> list = new ArrayList<>();
        while (i < 5) {
            Future<?> submit = threadPool.submit(resultThread);
            //等待子线程执行完毕，让子线程插队
            System.out.println("我先执行2");
            list.add(submit);
            i++;
        }
        for (Future future : list) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("遇到错误了！");
            }
        }

    }
}

class ResultThread implements Runnable {
    Thread thread;

    public ResultThread() {
    }

    public ResultThread(Thread thread) {
        this.thread = thread;
    }


    @Override
    public void run() {
        //如果要插队线程不为null ，那就执行插队 命令
        if (thread != null) {
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("我先执行");
        int i = 1 / 0;
        //这个异常 只有在主线程调用get 的时候才可以被主线程捕获到，否则 即使报错了 主线程也不会被感知也不会抛出
    }
}