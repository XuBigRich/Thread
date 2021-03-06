package xitongxuexi.Bcourse.ConditionsForWaiting.countDownLatch;

import xitongxuexi.Acourse.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 演示ContDownLatch ，有5个初始化的线程，6个扣除点
 * 扣除完毕以后 主线程 和业务线程 才可以继续工作
 *
 * 根据扣减点标识  当扣减点标识扣到0时  所有latch.await的方法才会继续进行
 */
public class UseContDownLatch {
    // 当CountDownLatch扣减到0时 所有的latch.await才会继续往下进行
    static CountDownLatch latch = new CountDownLatch(6);

    //初始化线程
    private static class InitThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread_" + Thread.currentThread().getId() + "ready init work............");
            latch.countDown(); //初始化线程完成 工作
            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_" + Thread.currentThread().getId() + "............continue do its work");
            }
        }
    }

    //业务线程
    private static class BusiThread implements Runnable {
        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread_" + Thread.currentThread().getId() + "............do Business~~~");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //单独的初始化线程
        new Thread(() -> {
            SleepTools.ms(1);
            System.out.println("Thread_" + Thread.currentThread().getId() + "ready init work step ls..........");
            latch.countDown();
            System.out.println("begin step 2nd");
            SleepTools.ms(1);
            latch.countDown();
            System.out.println("Thread_" + Thread.currentThread().getId() + "ready init work step 2nd.........");
        }).start();
        new Thread(new BusiThread()).start();
        for (int i = 0; i <= 3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();
        System.out.println("Main do ites work .....");
    }
}
