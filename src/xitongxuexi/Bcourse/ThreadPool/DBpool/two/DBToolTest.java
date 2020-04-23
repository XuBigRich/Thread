package xitongxuexi.Bcourse.ThreadPool.DBpool.two;

import xitongxuexi.Acourse.tools.SleepTools;

import java.sql.Connection;
import java.util.Random;

/**
 *
 */
public class DBToolTest {
    private static DBPoolSemaphore poolSemaphore=new DBPoolSemaphore();
    //拿数据库连接的线程
    private static class BusiThread extends Thread{
        @Override
        public void run(){
            Random r=new Random();
            long start=System.currentTimeMillis();
            try {
                Connection connection=poolSemaphore.takeConnection();
                System.out.println("Thread_"+Thread.currentThread().getId()+"获取数据库连接共用时:["+(System.currentTimeMillis()-start)+"]");
                //模拟业务操作
                SleepTools.ms(100+r.nextInt(100));
                //归还数据库连接
                poolSemaphore.returnConnect(connection) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            BusiThread busiThread=new BusiThread();
            busiThread.start();
        }
    }
}
