package xitongxuexi.Ccourse.AQS.interrupted;


/*
 * 类说明 :测试显示锁 await /signal/lock
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

        Thread.sleep(1000);
        express.changeSite();
    }
}
