package xitongxuexi.Bcourse.ThreadPool.Executor;


import xitongxuexi.Bcourse.ThreadPool.MakeArray;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumMultiThreads {
    /*每组线程计算量为1000个*/
    public final static int NUM=1000;
    public static long sum(int[] arr, ExecutorService executor) throws ExecutionException, InterruptedException {
        long result=0;
        int numThreads=arr.length/NUM>0?arr.length/NUM:1;
        int num=arr.length/numThreads;
        /*建立一个SumTask数组 里面有想要建立线程池所需要的所有 SumTask 任务  */
        SumTask[] sumTasks=new SumTask[numThreads];
        /*他是用于接收线程Callable接口 返回值时的 一个Future接口  数组个数为  线程池中任务的个数*/
        Future<Long>[] sums=new Future[numThreads];
        for(int i=0;i<numThreads;i++){
            /*把所有拆分的任务放入sumTask数组当中*/
            sumTasks[i]=new SumTask(arr,(i*NUM),((i+1)*NUM));
            /*等号右边：把所有任务依次提交给执行服务的excutor
              等号左边：将其结果 返回给事先准备好的Future*/
            sums[i]=executor.submit(sumTasks[i]);
        }
        for(int i=0;i<numThreads;i++){
            result+=sums[i].get();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*生成需要计算的数组*/
        int[] array=MakeArray.makeArray();
        /*所创建的线程 个数   个数=数组长度/每组线程计算个数*/
        int numThreads=array.length/NUM>0?array.length/NUM:1;
        /*创建一个线程池  Executors.NEW一个事先准备好的线程池数量为（）*/
        ExecutorService service= Executors.newFixedThreadPool(numThreads);
        long result =sum(array,service);
        System.out.println(result);
    }
}
