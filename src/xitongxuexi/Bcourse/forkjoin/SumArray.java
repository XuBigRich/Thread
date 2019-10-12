package xitongxuexi.Bcourse.forkjoin;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SumArray {
    public static class SumTask extends RecursiveTask<Integer> {
        private final static int THRESHOLD=MakeArray.ARRAY_LENGTH/4;  //数组总长度 除以 10  4000/10=400
        private int[] src;
        private int fromIndex;
        private int toIndex;
        public SumTask(int[] src,int fromIndex,int toIndex){
            this.src=src;
            this.fromIndex=fromIndex;
            this.toIndex=toIndex;
        }
        @Override
        protected Integer compute() {
            if(fromIndex+toIndex<THRESHOLD){    // 当 起始位置 到 结束位置 中间 数字 小于 阈值   就不拆了 
                int count=0;
                for(int i=fromIndex;i<toIndex;i++){
                    count=count+src[i];
                }
                System.out.println(count);
                return count;
            }else {
                int mid=(fromIndex+toIndex)/2;
                SumTask left= new SumTask(src,fromIndex,mid);  //递归调用
                SumTask right=new SumTask(src,mid+1,toIndex);  
                 return left.join()+right.join();

            }
        }
}

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();
        int[] src=MakeArray.makeArray();
        SumTask innerFind=new SumTask(src,0,src.length-1);
        long start=System.currentTimeMillis();
        pool.invoke(innerFind);
        System.out.println("完成");
        System.out.println("Task is Running.....  /n The count is "+ innerFind.join() +" spend time:" +(System.currentTimeMillis()-start)+"ms");
    }
}
