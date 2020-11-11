package xitongxuexi.Ccourse.AQS.custom;

import xitongxuexi.Acourse.tools.SleepTools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义一个AQS锁
 * @Author： hongzhi.xu
 * @Date: 2020/10/26 2:40 下午
 * @Version 1.0
 */
public class TestMyLock {
    public void test() {
        final Lock lock = new ReentrantLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepTools.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepTools.second(1);
                    } finally {
                        lock.unlock();
                    }
                    SleepTools.second(2);
                }
            }
        }
        //启动10个子线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        //主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepTools.second(1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestMyLock testMyLock=new TestMyLock();
        testMyLock.test();
    }
}
