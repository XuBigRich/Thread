package xitongxuexi.Acourse.volatil;


import java.util.concurrent.CountDownLatch;

/**
 * 验证volatile的可见性
 *  @Author： hongzhi.xu
 * @Date: 2020/7/25 10:17 上午
 * @Version 1.0
 */
public class VolatileVisibilitySample {
    /**
     * 不添加volatile的Flag变量
     */
    private   boolean  initFlag = false;
    /**
     * 添加volatile的Flag变量
     */
//    private  boolean  initFlag = false;
    /**
     *  使用CountDownLatch让线程0与1 同时运行
     */
    static CountDownLatch latch = new CountDownLatch(2);

    public void refresh() {
        this.initFlag = true;
        String threadname = Thread.currentThread().getName();
        System.out.println("线程：" + threadname + "修改了共享变量initFlag");
    }

    public void load() {
        String threadname = Thread.currentThread().getName();
        int i = 0;
        //如果initFlag一直为false那么循环将一直进行
        while (!initFlag) {
        }
        System.out.println("线程：" + threadname + "当前线程嗅探到initFlag的状态改变");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileVisibilitySample volatileVisibilitySample = new VolatileVisibilitySample();
        Thread t1 = new Thread(() -> {
            latch.countDown();
            volatileVisibilitySample.load();
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            latch.countDown();
            volatileVisibilitySample.load();
        });
        t2.start();
        //让前面两个线程先运动起来，使用initFlag  然后让t3线程修改Flag变量，看前面两个线程是否能嗅探到Flag变量的改变
        Thread.sleep(2000);
        Thread t3 = new Thread(() -> {
            volatileVisibilitySample.refresh();
            volatileVisibilitySample.load();
        });
        t3.start();
    }
}
