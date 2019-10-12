package xitongxuexi.Bcourse.forkjoin.B;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/*使用递归的方式 计算和*/
public class DGSumMultiThreads {
    /*每个线程计算的数据量*/
    public final static int NUM=500;
    public static long sum(ExecutorService service,int arry[],ArrayList<SumTask> sumTasks) throws ExecutionException, InterruptedException {
        Long result=0l;
        /*计算当前实际的线程数 如果每个线程计算量大于500个  那么使用数组长度*/
        int  threadNum=arry.length/2>500?arry.length/2:1;
        /*当实际的线程数大于目标线程数*/
        if(threadNum>arry.length/NUM){
            for(int i=0;i<threadNum;i++) {
                SumTask sumTask = new SumTask(arry, threadNum - 1 * 500, threadNum * 500);
                sumTasks.add(sumTask);
            }
            sum(service,arry,sumTasks);
        }else {
            Future<Long>[] futures=new Future[sumTasks.size()];
            for(int i=0;i<sumTasks.size();i++){
                futures[i]= service.submit(sumTasks.get(i));
                result+=futures[i].get();
            }
        }

        return result;

    }
}
