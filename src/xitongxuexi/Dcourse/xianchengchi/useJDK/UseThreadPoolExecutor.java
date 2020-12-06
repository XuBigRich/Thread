package xitongxuexi.Dcourse.xianchengchi.useJDK;


import org.omg.SendingContext.RunTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 示范使用JDK提供的线程池
 * <p>
 * ThreadPoolExecutor类构造方法各参数含义
 * <p>
 * int corePoolSize         线程池中核心线程数（核心线程数就是始终保持存活的线程数大小），线程池中核心线程数<corePoolSize 创建线程，线程池中核心线程数 = corePoolSize 任务会储存在BlockingQueue，
 * int maximumPoolSize      允许最大线程数，当BlockingQueue满了，如果线程数小于maximumPoolSize时，那么就会临时创建新的线程出现
 * long keepAliveTime       线程空闲下来 存活的时间，这个参数只在 线程数>corePoolSize 才有用
 * TimeUnit unit            存活时间单位值
 * BlockingQueue<Runnable> workQueue        保存任务的阻塞队列
 * ThreadFactory threadFactory              创建线程的工厂，给线程赋予名字
 * RejectedExecutionHandler handler         饱和策略 继承自RejectedExecutionHandler接口 有四个默认实现
 *
 *
 * <p>
 * ThreadPoolExecutor类提交任务方法
 * <p>
 * execute  ----- 提交  Runnable 类型 任务
 * submit ------提交  Callable 类型 任务  这个方法在ThreadPoolExecutor父类里面
 *
 * @Author： hongzhi.xu
 * @Date: 2020/12/4 上午10:22
 * @Version 1.0
 */
public class UseThreadPoolExecutor {


    static class RunnableWorker implements Runnable {
        public final static int Normal = 0;
        public final static int HasException = -1;
        public final static int ProcessException = 1;

        public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private int taskType;

        public RunnableWorker(int taskType) {
            this.taskType = taskType;
        }

        @Override
        public void run() {
            if (taskType == HasException) {
                System.out.println(simpleDateFormat.format(new Date()) + " Exception made .... ____By "+Thread.currentThread().getName());
                throw new RuntimeException("HasException Happen");
            } else if (taskType == ProcessException) {
                try {
                    System.out.println(simpleDateFormat.format(new Date()) + " Exception made,but catch ____By "+Thread.currentThread().getName());
                    throw new RuntimeException("HasException Happen");
                } catch (Exception e) {
                    System.out.println(" Exception made,but catch ");
                }
            }else if(taskType==Normal){
                System.out.println(simpleDateFormat.format(new Date())  +"Normal  ____By " +Thread.currentThread().getName());
            }
        }
    }

    /**
     * 里面包含 各种通过ThreadPoolExecutor 生成的线程池
     * 但是其中newWorkStealingPool是个例外，他是根据forkjoin生成的
     */
    public Executors executors;
    /**
     * cpu的逻辑核个数
     */
    public int cpuProcessors = Runtime.getRuntime().availableProcessors();

    /**
     * 自己创建一个线程池
     */
    public ExecutorService selfCreateThreadPool() {
        ExecutorService pool = new ThreadPoolExecutor(
                2,
                4,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        return pool;

    }


    public void testThreadPool() {
        ExecutorService executorService = selfCreateThreadPool();
        executorService.execute(() -> System.out.println("嘿嘿"));

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        //执行一个常规的定时线程任务
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new RunnableWorker(RunnableWorker.Normal), 1000, 3000, TimeUnit.MILLISECONDS);
        //执行一个会抛出异常的 定时任务（当抛出异常后，线程将会死亡）
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new RunnableWorker(RunnableWorker.HasException), 1000, 3000, TimeUnit.MILLISECONDS);
        //执行一个会捕获异常的 定时任务（当抛出异常后，线程会捕获，线程并不会死亡）
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new RunnableWorker(RunnableWorker.ProcessException), 1000, 3000, TimeUnit.MILLISECONDS);


    }

    public static void main(String[] args) {
        UseThreadPoolExecutor useThreadPoolExecutor = new UseThreadPoolExecutor();
        useThreadPoolExecutor.testThreadPool();

    }
}
