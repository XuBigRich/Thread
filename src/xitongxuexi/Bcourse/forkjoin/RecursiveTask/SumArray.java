package xitongxuexi.Bcourse.forkjoin.RecursiveTask;


import xitongxuexi.Bcourse.ThreadPool.MakeArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用多线程ForkJoin池的方法 计算
 * RecursiveTask表示 forkjoin有返回结果
 */
public class SumArray {
    public static class SumTask extends RecursiveTask<Long> {
        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 10;  //数组总长度 除以 10  4000/10=400
        private int[] src;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Long compute() {
            List<SumTask> sumTasks = new ArrayList<>();
            if (toIndex - fromIndex < THRESHOLD) {    // 当 起始位置 到 结束位置 中间 数字 小于 阈值   就不拆了
                Long count = 0l;
                for (int i = fromIndex; i <= toIndex; i++) {
                    count = count + src[i];
                }
                return count;
            } else {
                int mid = (fromIndex + toIndex) / 2;
                SumTask left = new SumTask(src, fromIndex, mid);  //递归调用
                SumTask right = new SumTask(src, mid + 1, toIndex);
                //传可变参/定参的方法 使用forkjoin
                invokeAll(left, right);
//                return left.join()+right.join();
                //========================================================
                // 使用容器的方法 使用forkjoin
                sumTasks.add(left);
                sumTasks.add(right);
                Long result = 0l;
                for (SumTask sumTask : sumTasks) {
                    result += sumTask.join();
                }
                return result;
            }
        }
    }

    public static void main(String[] args) {
        //forkjoinpool可以理解为 forkjoin框架的执行器
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MakeArray.makeArray();
        //此处声明总任务
        SumTask innerFind = new SumTask(src, 0, src.length - 1);
        long start = System.currentTimeMillis();
        pool.invoke(innerFind);
        System.out.println("完成");
        System.out.println("Task is Running.....  /n The count is " + innerFind.join() + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
