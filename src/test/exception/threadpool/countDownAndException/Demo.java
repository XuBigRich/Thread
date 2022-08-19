package test.exception.threadpool.countDownAndException;

import test.exception.threadpool.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 许鸿志
 * @since 2022/8/19
 */
public class Demo {
    static CountDownLatch countDownLatch = new CountDownLatch(5);
    static ExecutorService threadPool = ThreadPoolUtils.getThreadPool();

    static class ResultThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我先执行");
            //这个异常 只有在主线程调用get 的时候才可以被主线程捕获到，否则 即使报错了 主线程也不会被感知也不会抛出
            int i = 1 / 0;

        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //给每个学生开启一个线程，判断学生答题结果
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //生成学生作答试卷基础对象，拿到当前学生作答试卷的情况
            Future<?> submit = ThreadPoolUtils.getThreadPool().submit(new ResultThread());
            futures.add(submit);
            countDownLatch.countDown();
        }

        /**
         * 下面这两个for循环 虽然 前后顺序 不会影响（变相说明虽然future返回的是空，但依然需要方法执行完毕才可以返回）
         * 但是依然建议执行完countDown 之后在执行 判断异常的这个 顺序   get会阻塞线程运行
         */
        //校验一下  处理过程中有没有错
//        for (Future future : futures) {
//            future.get();
//        }
        System.out.println("获取完毕");
        countDownLatch.await();
        //校验一下  处理过程中有没有错
        for (Future future : futures) {
            future.get();
        }
    }
}
