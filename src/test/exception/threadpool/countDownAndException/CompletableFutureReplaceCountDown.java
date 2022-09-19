package test.exception.threadpool.countDownAndException;

import test.exception.threadpool.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * CompletableFuture替换CountDown
 *
 * @author 许鸿志
 * @since 2022/9/19
 */
public class CompletableFutureReplaceCountDown {
    static ExecutorService threadPool = ThreadPoolUtils.getThreadPool();

    public static void main(String[] args) {
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //生成学生作答试卷基础对象，拿到当前学生作答试卷的情况
            CompletableFuture<?> submit = CompletableFuture.runAsync(new ResultThread(), threadPool);
            completableFutureList.add(submit);
        }

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
        //当执行完毕后调用  他是一个异步回调，并不在主线程中执行
        voidCompletableFuture.whenComplete((r, b) -> {
            System.out.println(r.getClass() + " " + b.getClass());
        }).join();
        //让主线程等待所有任务执行完毕  ，主线程一定要等回调方法执行后再 退出，否则可能会收不到  异常
        voidCompletableFuture.join();
        System.out.println("我执行了");

    }
}
