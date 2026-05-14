package xitongxuexi.Bcourse.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * 除了串行执行外，多个CompletableFuture还可以并行执行。例如，我们考虑这样的场景：
 * <p>
 * 同时从新浪和网易查询证券代码，只要任意一个返回结果，就进行下一步查询价格，查询价格也同时从新浪和网易查询，只要任意一个返回结果，就完成操作：
 *上述逻辑实现的异步查询规则实际上是：
 *
 * ┌─────────────┐ ┌─────────────┐
 * │ Query Code  │ │ Query Code  │
 * │  from sina  │ │  from 163   │
 * └─────────────┘ └─────────────┘
 *        │               │
 *        └───────┬───────┘
 *                ▼
 *         ┌─────────────┐
 *         │    anyOf    │
 *         └─────────────┘
 *                │
 *        ┌───────┴────────┐
 *        ▼                ▼
 * ┌─────────────┐  ┌─────────────┐
 * │ Query Price │  │ Query Price │
 * │  from sina  │  │  from 163   │
 * └─────────────┘  └─────────────┘
 *        │                │
 *        └────────┬───────┘
 *                 ▼
 *          ┌─────────────┐
 *          │    anyOf    │
 *          └─────────────┘
 *                 │
 *                 ▼
 *          ┌─────────────┐
 *          │Display Price│
 *          └─────────────┘
 * 除了anyOf()可以实现“任意个CompletableFuture只要一个成功”，allOf()可以实现“所有CompletableFuture都必须成功”，这些组合操作可以实现非常复杂的异步流程控制。
 *
 * 最后我们注意CompletableFuture的命名规则：
 *
 * xxx()：表示该方法将继续在已有的线程中执行；
 * xxxAsync()：表示将异步在线程池中执行。
 *
 * @author 许鸿志
 * @since 2022/9/20
 */
public class ParallelDemo {
    public static void paralle() {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);


        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });


        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);
        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
    }

    public static void main(String[] args) throws Exception {
        paralle();
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
