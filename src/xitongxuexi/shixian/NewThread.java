package xitongxuexi.shixian;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName NewThread.java
 * @Description TODO
 * @createTime 2019年04月09日 08:21:00
 */
public class NewThread {
    private static class UseRun implements Runnable{

        @Override
        public void run() {
            System.out.println("i am implements Runnable");
        }
    }
    private static class UseCall implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("i am implements Callable");
            return "CallResult";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseRun useRun=new UseRun();
//            useRun.run();
            new Thread(useRun).start();
        UseCall useCall=new UseCall();
        FutureTask<String> futureTask=new FutureTask<>(useCall);
//        useCall.call();
        Thread thread=new Thread(futureTask);
        thread.start();
        for(int i=0;i<10;i++){
            new Thread(useRun).start();
            new Thread(new FutureTask<>(useCall)).start();
        }
        System.out.println(futureTask.get());
    }

}
