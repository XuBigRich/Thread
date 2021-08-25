package test.printABC;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 朱克然所写的顺序输出ABC
 *
 * @author 许鸿志
 * @since 2021/8/25
 */
public class Zhu {
    AtomicInteger a = new AtomicInteger(1);
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    Runnable runnable = () -> {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                if (a.get() != 1) {
                    condition.await();
                }
                System.out.println("A");
                a.set(2);
                condition1.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    };

    Runnable runnable1 = () -> {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                if (a.get() != 2) {
                    condition1.await();
                }
                System.out.println("B");
                a.set(3);
                condition2.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    };

    Runnable getRunnable2 = () -> {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                if (a.get() != 3) {
                    condition2.await();
                }
                System.out.println("C");
                a.set(1);
                condition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    };

    public static void main(String[] args) {
        Zhu zhu = new Zhu();
        Thread thread = new Thread(zhu.runnable);
        Thread thread1 = new Thread(zhu.runnable1);
        Thread thread2 = new Thread(zhu.getRunnable2);
        thread.start();
        thread1.start();
        thread2.start();
    }
}
