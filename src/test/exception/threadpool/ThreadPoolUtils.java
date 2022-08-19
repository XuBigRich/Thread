package test.exception.threadpool;

import java.util.concurrent.*;

public  class ThreadPoolUtils {
    private static final ExecutorService threadPool;

    static {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 5;
        TimeUnit keepAliveTimeUnit = TimeUnit.MINUTES;
        int queSize = 1000;
        ThreadFactory threadFactory = new DefaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, keepAliveTimeUnit, new ArrayBlockingQueue<>(queSize)
                , threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }


    /**
     * 获取线程池
     * @return 线程池
     */
    public static ExecutorService getThreadPool() {
        return threadPool;
    }

}
