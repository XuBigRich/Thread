package test.printABC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这是一个会产生死锁的demo
 * 具体产生死锁原因 看代码
 * <p>
 * 其实这个 类执行的结果 虽然大部分是abc ，但是很隐秘的 会存在一些 顺序不对的情况
 * 如:
 * B
 * A
 * A
 * C
 * C
 * A
 * B
 * B
 * C
 *
 * @author 许鸿志
 * @since 2021/8/25
 */
public class DeadLock {
    static AtomicInteger ai = new AtomicInteger(3);
    static CountDownLatch countDownLatch = new CountDownLatch(3);
    Runnable runnable = () -> {
        for (int i = 0; i < 1000000; i++) {
            try {
                while (!(ai.get() % 3 == 0)) {
                    //输出这一行更容易造成死锁
                    System.out.println("1111");
                    synchronized (this) {
                        wait();
                    }
                }
                //下面这两行是死锁的关键原因，因为下面这两行在执行时不会加锁，当执行完 ai.getAndIncrement();  后，恰巧另外两个 线程 在执行while 判断，
                //然后其中一个 条件恰好又成立，也走到了  ai.getAndIncrement()，另一个 因为 前面的线程已经执行过ai+1，所以也成立，于是乎，三个线程同时走到了，
                // notifyAll();
                //这时，他们三个线程中有一个线程肯定可以跳过wile循环继续输出A、B、C，但是
                //举个例子，A 执行完  ai.getAndIncrement(); 后，B好刚接收到一个notifyAll 命令，需要进行再次判断，（注意此时A的notifyAll并未发出）
                // 于是恰恰此时的 while条件判断失败，B也开始执行输出打印
                //首先 明确 产生死锁关键原因，是因为    notifyAll();使用完了，无法唤醒新的wait 进行等待解除
                //当其中一个while 成立的线程走到了 System.out.println("1111");时 时间片切换到另一个地方，执行notifyAll，然后就此 这个线程沉睡，没人可以唤醒他了
                //A 刚刚执行完毕
                //A ->     ai.getAndIncrement();  B 可以执行
                //B ->  ai.getAndIncrement(); C可以执行
                //C -> ai.getAndIncrement(); A可以执行
                //以上他们都同时执行完
                //接下来 A先执行了，判断自己不需要wait ,继续执行到A（暂未执行） ->     ai.getAndIncrement();  （此时已经输出了两个A了）
                //接着B执行执行notifyAll ，然后B判断自己不可以执行，进入System.out.println("1111");
                //然后时间片轮转C执行notifyAll，
                //紧接着 A继续执行， B依旧停留在System.out.println("1111");
                //A执行notifyAll，B依旧没有执行到wait  ，所以呀，norifyAll用完了 ，没有线程去再唤醒他了，
                //于是三个线程都走到了wait  造成了死锁
                ai.getAndIncrement();
                System.out.println("A");
                synchronized (this) {
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };
    Runnable runnable1 = () -> {
        for (int i = 0; i < 1000000; i++) {
            try {
                while (!(ai.get() % 3 == 1)) {
                    synchronized (this) {
                        wait();
                    }
                }
                ai.getAndIncrement();
                System.out.println("B");
                synchronized (this) {
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };

    Runnable runnable2 = () -> {
        for (int i = 0; i < 1000000; i++) {
            try {
                while (!(ai.get() % 3 == 2)) {
                    synchronized (this) {
                        wait();
                    }
                }
                ai.getAndIncrement();
                System.out.println("C");
                synchronized (this) {
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    };

    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();
        Thread thread = new Thread(deadLock.runnable);
        Thread thread1 = new Thread(deadLock.runnable1);

        Thread thread2 = new Thread(deadLock.runnable2);

        thread2.start();
        thread1.start();
        thread.start();
    }

}
