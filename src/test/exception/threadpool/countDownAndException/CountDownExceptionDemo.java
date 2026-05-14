package test.exception.threadpool.countDownAndException;

import test.exception.threadpool.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 使用CountDownLath 让一组 任务执行完毕后，然后一起继续执行程序。
 * **** 如若执行过程中产生报错，等待线程需要得知，并有能力做出相应处理  ***************
 * 步骤：
 * 1.声明一个Future 列表
 * 2.在for循环中 通过线程池提交 线程任务的方式 生成Future对象（不管是否有返回值）
 * 3.将生成的future独对象放入List中
 * 4.使用future.get 获取 在程序执行完毕后的结果  *****如若有异常：将抛出异常**********
 * 优化：
 * JDK提供了 新的方案 可以体面的 去处理程序的异常。
 * CompletableFuture
 *
 * @author 许鸿志
 * @since 2022/8/19
 */
public class CountDownExceptionDemo {
    static CountDownLatch countDownLatch = new CountDownLatch(5);



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
        //如果出错，等待线程（主线程） 有能力进行 异常的捕获与处理
        try {
            for (Future future : futures) {
                future.get();
            }
        } catch (Exception e) {
            System.out.println("有异常产生，你需要进行处理");
        }
    }
}
