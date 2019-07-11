package xitongxuexi.Bcourse.xianchengchi;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBToolTest {
    static DBPool pool=new DBPool(10);
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        /*指定线程数量*/
        int threadCount=50;
        end=new CountDownLatch(threadCount);
        int count=20;//每个线程操作次数
        AtomicInteger got=new AtomicInteger();//计数器，统计可以拿到连接的线程个数
        AtomicInteger notGot=new AtomicInteger();//计数器，统计没有拿到连接的线程个数
        for(int i=0;i<threadCount;i++){
            Thread thread=new Thread(new Worker(count,got,notGot),"worker_"+i);
            thread.start();
        }
        end.await();
        System.out.println("总共尝试了："+(threadCount*count));
        System.out.println("拿到连接的次数"+got);
        System.out.println("没能拿到连接的次数"+notGot);
    }
    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;
        public Worker(int count,AtomicInteger got,AtomicInteger notGot){
            this.count=count;
            this.got=got;
            this.notGot=notGot;
        }

        @Override
        public void run() {
            while(count>0){
                /*
                * 从线程池中获取连接，如果在1000ms内没有获取到  将会返回Null
                * 分别统计连接获取的数量got和未h获取到的数量notGot
                * */
                try {
                    Connection connection=pool.fetchConn(1000);
                    if(connection!=null) {  //如果连接对象获取成功
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    }else {             //else 如果连接对象获取未成功
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+"等待超时");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
