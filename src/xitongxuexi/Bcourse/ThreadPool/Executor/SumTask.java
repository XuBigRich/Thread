package xitongxuexi.Bcourse.ThreadPool.Executor;

import java.util.concurrent.Callable;

public class SumTask implements Callable<Long> {
    int[] arr;
    int begin;
    int end;

    public SumTask(int[] arr, int begin, int end) {
        this.arr = arr;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Long call() throws Exception {
        long result=SumUtils.sumRange(arr,begin,end);
        return result;
    }
}
