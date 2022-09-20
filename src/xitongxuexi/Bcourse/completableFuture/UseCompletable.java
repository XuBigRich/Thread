package xitongxuexi.Bcourse.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture可以指定异步处理流程：
 *
 * thenAccept()处理正常结果；
 * exceptional()处理异常结果；
 * thenApplyAsync()用于串行化另一个CompletableFuture；
 * anyOf()和allOf()用于并行化多个CompletableFuture。
 * @author 许鸿志
 * @since 2022/9/19
 */
public class UseCompletable {
    public static List tasks = new ArrayList();

    /**
     * run的参数是Runnable，而supply的参数是Supplier。前者没有返回值，而后者有，否则没有什么两样。
     * <p>
     * 这两组静态函数，都提供了传入自定义线程池的功能。如果你用的不是外置的线程池，那么它就会使用默认的ForkJoin线程池。
     * 默认的线程池，大小和用途你是控制不了的，所以还是建议自己传递一个。
     */
    public static void baseAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "test";
        });
        String result = future.join();
    }

    public static void baseRunnable() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("test");
        });
        Void result = future.get();
    }

    /**
     * apply 有入参和返回值，入参为前置任务的输出
     * accept 有入参无返回值，会返回CompletableFuture
     * run 没有入参也没有返回值，同样会返回CompletableFuture
     * combine 形成一个复合的结构，连接两个CompletableFuture，并将它们的2个输出结果，作为combine的输入
     * compose 将嵌套的CompletableFuture平铺开，用来串联两个CompletableFuture
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void thenApplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1);

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * 没有入参也没有返回值，同样会返回CompletableFuture
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void thenAcceptAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenAcceptAsync(e -> System.out.println(e));

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * 形成一个复合的结构，连接两个CompletableFuture，并将它们的2个输出结果，作为combine的输入
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void thenCombineAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenCombineAsync(CompletableFuture.supplyAsync(() -> 10), (e, f) -> {
                    System.out.println(e + " " + f);
                    return e + f;
                });

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * 将嵌套的CompletableFuture平铺开，用来串联两个CompletableFuture
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void thenComposeAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenComposeAsync(e -> CompletableFuture.supplyAsync(() -> 10 + e));

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * when的意思，就是任务完成时候的回调。比如我们上面的例子，打算在完成任务后，输出一个done。它也是属于只有入参没有出参的范畴，适合放在最后一步进行观测。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void whenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1)
                .whenComplete((r, e) -> {
                    System.out.println("done");
                });

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * CompletableFuture的任务是串联的，如果它的其中某一步骤发生了异常，会影响后续代码的运行的。
     * <p>
     * exceptionally从名字就可以看出，是专门处理这种情况的。比如，我们强制某个步骤除以0，发生异常，捕获后返回-1，它将能够继续运行。
     *
     * @param
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void exceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(e -> e / 0)
                .thenApplyAsync(e -> e - 1)
                .exceptionally(ex -> {
                    System.out.println(ex);
                    return -1;
                });

        cf.join();
        System.out.println(cf.get());
    }

    /**
     * handle更加高级一些，因为它除了一个异常参数，还有一个正常的入参。处理方法也都类似，不再赘述。
     * <p>
     * 当然，CompletableFuture的函数不仅仅这些，还有更多，根据函数名称很容易能够了解到它的作用。它还可以替换复杂的CountDownLatch，这要涉及到几个比较难搞的函数。
     *
     * @param
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void handle() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(e -> e / 0)
                .thenApplyAsync(e -> e - 1)
                .handle((e,ex) -> {
                    System.out.println("异常："+ex);
                    System.out.println("参数: "+e);
                    return -1;
                });

        cf.join();
        System.out.println(cf.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //CompletableFuture对象创建
//        baseRunnable();
//        baseAsync();
        //结果返回处理
        //多个CompletableFuture可以串行执行
//        thenApplyAsync();
//        thenAcceptAsync();
//        thenCombineAsync();
//        thenComposeAsync();
        //结果回调
//        whenComplete();
//        exceptionally();
        handle();
    }
}
