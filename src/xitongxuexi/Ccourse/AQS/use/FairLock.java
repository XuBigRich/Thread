package xitongxuexi.Ccourse.AQS.use;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示公平锁
 * <p>
 * 公平锁 会按照持有锁的顺序 逐个执行
 *
 * @Author： hongzhi.xu
 * @Date: 2020/11/12 7:16 下午
 * @Version 1.0
 */
public class FairLock {
    private ReentrantLock lock = new ReentrantLock(true);

    public void fairLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在持有锁");
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁");

        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            fairLock.fairLock();
        };
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}
