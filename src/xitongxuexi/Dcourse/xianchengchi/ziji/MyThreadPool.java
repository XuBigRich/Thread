package xitongxuexi.Dcourse.xianchengchi.ziji;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 自己瞎胡诌一个线程池
 * @Author： hongzhi.xu
 * @Date: 2020/12/3 上午10:21
 * @Version 1.
 *
 */
public class MyThreadPool {
    //线程池中默认线程个数
    private static int WORK_NUM = 5;
    //队列默认任务个数100
    private static int TASK_COUNT = 1000;

    //工作线程数
    private WorkThread[] workerThreads;
    //任务队列
    private final BlockingQueue<Runnable> taskQueue;
    private final int worker_num;//用户在构造这个池时，希望启动的线程数

    public MyThreadPool() {
        this(WORK_NUM, TASK_COUNT);
    }

    public MyThreadPool(int worker_num, int taskCount) {
        if (worker_num < 0) worker_num = WORK_NUM;
        if (taskCount < 0) taskCount = TASK_COUNT;
        this.worker_num = worker_num;
        taskQueue = new ArrayBlockingQueue<>(taskCount);
        workerThreads = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workerThreads[i] = new WorkThread();
            workerThreads[i].start();
        }
    }

    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        System.out.println("ready close pool.....");
        taskQueue.clear();
        for (int i = 0; i < worker_num; i++) {
            workerThreads[i].stopWorker();
            workerThreads[i] = null;
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()) {

                    r = taskQueue.take();
                    if (r != null) {
                        r.run();
                    }
                    r = null;
                }
            } catch (InterruptedException e) {
                System.out.println("线程调用结束");
            }
        }

        public void stopWorker() {
            interrupt();
        }
    }


}
