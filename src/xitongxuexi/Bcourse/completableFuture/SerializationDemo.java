package xitongxuexi.Bcourse.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * 如果只是实现了异步回调机制，我们还看不出CompletableFuture相比Future的优势。
 * CompletableFuture更强大的功能是，多个CompletableFuture可以串行执行，
 * 例如，定义两个CompletableFuture，
 * 第一个CompletableFuture根据证券名称查询证券代码，
 * 第二个CompletableFuture根据证券代码查询证券价格，
 * 这两个CompletableFuture实现串行操作如下：
 * @author 许鸿志
 * @since 2022/9/20
 */
public class SerializationDemo {


    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }

    /**
     * 串行化处理任务
     *
     * @return
     */
    public static CompletableFuture<Void> serialization() {
        // 第一个任务:       生成CompletableFuture<String>对象
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油");
        });
        // cfQuery成功后继续执行下一个任务:  生成CompletableFuture<Double> 对象
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice(code);
        });
        // cfFetch成功后打印结果:
        CompletableFuture<Void> voidCompletableFuture = cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        return voidCompletableFuture;
    }

    public static void main(String[] args) throws Exception {
        serialization();
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }
}
