package jichu.threadAndSynchronized;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * 验证线程与Synchronized锁的关系
 * 查看是否 唯一的一个线程可以多次进入含有Synchronized关键字的方法
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 4:16 下午
 * @Version 1.0
 */
public class Verification {
    public synchronized void test() {
        System.out.println("进来了 开始休眠");
        SleepTools.ms(1000000);
        System.out.println("休眠结束 准备出去");
    }

    //Exce线程 执行run方法
    public static class Exce implements Runnable {
        public Verification verification;

        public Exce(Verification verification) {
            this.verification = verification;
        }

        @Override
        //这个线程疯狂的执行verification对象的test 方法，来检测 单线程是否可以 多次进入 含有synchronized关键字 并未退出的方法
        //原因是，这是一个但线程方法，当进入了test后 线程就会执行 test方法了，等test方法执行完之后才可以继续执行
        public void run() {
            for (int i = 0; i < 1000; i++) {
                verification.test();
            }
        }
    }

    public static void main(String[] args) {
        Exce exce=new Exce(new Verification());
        Thread thread=new Thread(exce);
        //启动一个线程
        thread.start();

        //测试第二个线程是否可以进入已经持有锁的方法
        Thread thread1=new Thread(exce);
        //启动第二个线程
        thread1.start();
    }
}
