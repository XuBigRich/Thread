package test.notSecurity;

import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class Handler implements Runnable {
    static CountDownLatch latch = new CountDownLatch(4);
    public Dispose dispose;
    public Map<Integer, String> map;

    public Handler(Dispose dispose, Map<Integer, String> map) {
        this.dispose = dispose;
        this.map = map;
    }

    @Override
    public void run() {
        String key = map.get(1);
        String valus = map.get(2);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //线程安全问题一般发生在下面
        try {
            // 调用此方法并未产生线程安全问题，因此怀疑，即使是同一个对象，每次调用方法时，也会去重新申请一块内存区域给方法
            dispose.addMap(key, valus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
