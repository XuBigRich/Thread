package xitongxuexi.Bcourse.notifyAll;

/*
 * 类说明 :测试wait /notify/notifyAll
 * */
public class TestWN {
    private static Express express = new Express(0, Express.CITY);

    public static class ChangeSite extends Thread {
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static class ChangeKm extends Thread {
        @Override
        public void run() {
            express.waitKm();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new ChangeSite().start();
        }
        for (int i = 0; i < 3; i++) {
            new ChangeKm().start();
        }

//        express.notifyAll();
        Thread.sleep(1000);
        express.changeKm();
        Thread.sleep(1000);
        //这个地方是有错误的  notifyAll与 wait方法 都会释放 锁
        // 但是 在这个地方调用 notifyAll 可是 main方法并没有 express对象的 对象锁 所以也就 无法释放 锁了 所以报错 IllegalMonitorStateException
        //如果当前线程不是对象所得持有者，该方法抛出一个java.lang.IllegalMonitorStateException 异常”
        //        express.notifyAll();

        //所以改进方法 将express对象上锁，然后在锁内执行notifyAll（）方法
        //必须注意，这个锁必须锁的是当前express对象不可以是别的
        synchronized (express) {
            express.notifyAll();
        }

        Thread.sleep(1000);
        express.changeSite();
    }
}
