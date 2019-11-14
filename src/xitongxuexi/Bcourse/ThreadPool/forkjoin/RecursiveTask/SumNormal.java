package xitongxuexi.Bcourse.ThreadPool.forkjoin.RecursiveTask;

import xitongxuexi.Acourse.tools.SleepTools;
import xitongxuexi.Bcourse.ThreadPool.MakeArray;
/*单线程运行*/
public class SumNormal {
    public static void main(String[] args) {
        int count=0;
        int[] src= MakeArray.makeArray();
        long start=System.currentTimeMillis();
        for(int i=0;i<src.length;i++){
            SleepTools.ms(1);
            count=count+src[i];
        }
        System.out.println("The count is "+ count +" spend time:" +(System.currentTimeMillis()-start)+"ms");
    }
}
