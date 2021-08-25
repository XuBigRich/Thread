package test.printABC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在不同的for循环中让他们依次执行 输出ABC
 *
 * @author 许鸿志
 * @since 2021/8/25
 */
public class Self {
    static AtomicInteger ai = new AtomicInteger(3);
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    Runnable runnable = () -> {
        for (int i = 0; i < 1000; i++) {
            try {
                while (!(ai.get() % 3 == 0)) {
                    synchronized (this) {
                        wait();
                    }
                }
                synchronized (this) {
                    ai.getAndIncrement();
                    System.out.println("A");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };

    Runnable runnable1 = () -> {
        for (int i = 0; i < 1000; i++) {
            try {
                while (!(ai.get() % 3 == 1)) {
                    synchronized (this) {
                        wait();
                    }
                }
                synchronized (this) {
                    ai.getAndIncrement();
                    System.out.println("B");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };

    Runnable runnable2 = () -> {
        for (int i = 0; i < 1000; i++) {
            try {
                while (!(ai.get() % 3 == 2)) {
                    synchronized (this) {
                        wait();
                    }
                }
                synchronized (this) {
                    ai.getAndIncrement();
                    System.out.println("C");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };
    //用于唤醒本对象的wait等待
    Runnable controller = () -> {
        while (true) {
            synchronized (this) {
                notifyAll();
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Self self = new Self();
        Thread thread = new Thread(self.runnable);
        Thread thread1 = new Thread(self.runnable1);
        Thread thread2 = new Thread(self.runnable2);
        //此线程专门用于唤醒wait等待 ,进行条件判断的
        new Thread(self.controller).start();
        Long start = System.currentTimeMillis();
        //开启众多线程
        thread2.start();
        thread1.start();
        thread.start();
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - start);
    }
}
