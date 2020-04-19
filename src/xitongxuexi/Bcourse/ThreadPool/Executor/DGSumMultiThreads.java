package xitongxuexi.Bcourse.ThreadPool.Executor;

import xitongxuexi.Bcourse.ThreadPool.MakeArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*使用递归的方式 计算和*/
//我觉得烂尾了
public class DGSumMultiThreads {
    /*每个线程计算的数据量*/
    public final static int NUM = 500;

    /**
     * 计算
     *
     * @param service   线程池
     * @param arry      要计算的数组
     * @param sumTasks  数组
     * @param threadNum 线程数
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static long sum(ExecutorService service, int arry[], ArrayList<SumTask> sumTasks, int threadNum) throws ExecutionException, InterruptedException {
        Long result = 0l;
        //根据线程数量创建 相应数量的线程任务
        for (int i = 1; i <= threadNum; ++i) {
            SumTask sumTask = new SumTask(arry, (i - 1) * 500, i * 500-1);
            sumTasks.add(sumTask);
        }
        Future<Long>[] futures = new Future[sumTasks.size()];
        for (int i = 0; i < sumTasks.size(); i++) {
            futures[i] = service.submit(sumTasks.get(i));
            result += futures[i].get();
        }
        return result;

    }

    /*每个线程计算的数据量*/
    public static long sum(ExecutorService service, int arry[], ArrayList<SumTask> sumTasks) throws ExecutionException, InterruptedException {
        Long result = 0l;
        /*计算当前实际的线程数 如果每个线程计算量大于500个  那么使用数组长度*/
        int threadNum = arry.length / 2 > 500 ? arry.length / 2 : 1;
        /*当实际的线程数大于目标线程数*/
        if (threadNum > arry.length / NUM) {
            for (int i = 0; i < threadNum; i++) {
                SumTask sumTask = new SumTask(arry, threadNum - 1 * 500, threadNum * 500);
                sumTasks.add(sumTask);
            }
            Future<Long>[] futures = new Future[sumTasks.size()];
            for (int i = 0; i < sumTasks.size(); i++) {
                futures[i] = service.submit(sumTasks.get(i));
                result += futures[i].get();
            }
            sum(service, arry, sumTasks);
        } else {


        }

        return result;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*生成需要计算的数组*/
        int[] array = MakeArray.makeArray();
        /*所创建的线程 个数   个数=数组长度/每组线程计算个数*/
        int numThreads = Math.max(array.length / NUM, 1);
        /*创建一个线程池  Executors.NEW一个事先准备好的线程池数量为（）*/
        ExecutorService service = Executors.newFixedThreadPool(numThreads);
        ArrayList<SumTask> sumTask = new ArrayList<>();
        long result = sum(service, array, sumTask, numThreads);
        System.out.println("是谁"+result);
    }
}
