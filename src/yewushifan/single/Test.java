package yewushifan.single;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/12/14 下午4:31
 * @Version 1.0
 */
public class Test {

   private ExecutorService pool = new ThreadPoolExecutor(
            20,
            100,
            3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
        Test test=new Test();
        test.pool.execute(new CreateSingleMode());
        test.pool.execute(new CreateSingleMode());
//        test.pool.execute(new CreateSingleModeB());
//        test.pool.execute(new CreateSingleModeB());
        test.pool.execute(new CreateSingleModeC());
        test.pool.execute(new CreateSingleModeC());
    }

   static class CreateSingleMode implements Runnable{

        @Override
        public void run() {
            SingleMode.getInstance();
        }
    }
    static class CreateSingleModeB implements Runnable{

        @Override
        public void run() {
            SingleMode.getInstance2();
        }
    }
    static class CreateSingleModeC implements Runnable{

        @Override
        public void run() {
            SingleMode.getInstance3();
        }
    }
}
