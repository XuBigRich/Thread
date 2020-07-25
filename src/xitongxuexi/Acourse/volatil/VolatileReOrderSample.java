package xitongxuexi.Acourse.volatil;

import java.util.concurrent.CountDownLatch;

/**
 * 验证volatile的有序性
 * @Author： hongzhi.xu
 * @Date: 2020/7/25 1:59 下午
 * @Version 1.0
 */
public class VolatileReOrderSample {
    private static int x=0,y=0;
    private static int a=0,b=0;

    public static void main(String[] args) throws InterruptedException {
        int i=0;
        for(;;){
            x=0;y=0;
            a=0;b=0;
            Thread t1=new Thread(()->{
                a=1;
                x=b;
            });
            Thread t2=new Thread(()->{
                b=1;
                y=a;
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(x+" "+y);
        }
    }
}
