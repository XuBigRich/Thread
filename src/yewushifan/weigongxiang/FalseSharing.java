package yewushifan.weigongxiang;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public final class FalseSharing {
    //控制线程个数 （静态）
    private final static int NUM_THREADS = 4; // change

    private static void runTest() throws InterruptedException {
        //建立大小为4的 线程数组 并对线程进行初始化
        Thread[] threads = new Thread[NUM_THREADS];
        //给每个线程放入要操作的 runnable，根据循环次数 给他初始化 也给种子 1，2，3，4
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseShare(i));
        }
        //启动所有线程
        for (Thread t : threads) {
            t.start();
        }
        //让线程开始插队，让他们都执行完，主线程再继续执行
        for (Thread t : threads) {
            t.join();
        }
    }

    //用于执行测试的main方法
    public static void main(final String[] args) throws Exception {
        //获取当前时间
        final long start = System.nanoTime();
        //执行静态方法
        runTest();
        //15301172800  31433589600
        System.out.println("duration = " + (System.nanoTime() - start));

        VolatileLong volatileLong = new VolatileLong();
        long objectSize = ObjectSizeCalculator.getObjectSize(volatileLong);
        System.out.println(objectSize);
    }


}