package xitongxuexi.Ccourse.LearLockSupport;

import xitongxuexi.Acourse.tools.SleepTools;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/30 2:10 下午
 * @Version 1.0
 */
public class UseLockSupport {

    static class WaitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("等待线程启动并开始等待");
            LockSupport.park();
            System.out.println("等待线程结束等待");
        }
    }
    static class RouseThread implements Runnable{
        Thread thread;

        public RouseThread(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("唤醒线程启动并开始唤醒");
            LockSupport.unpark(thread);
            System.out.println("唤醒线程结束唤醒");
        }
    }

    public static void main(String[] args) {
        WaitThread waitThread=new WaitThread();
        Thread wait=new Thread(waitThread);
        wait.start();

        SleepTools.ms(1000);
        RouseThread rouseThread=new RouseThread(wait);
        Thread rouse=new Thread(rouseThread);
        rouse.start();
    }
}
