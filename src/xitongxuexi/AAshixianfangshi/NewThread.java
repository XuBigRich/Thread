package xitongxuexi.AAshixianfangshi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName NewThread.java
 * @Description 启动线程的三种实现方式
 * @createTime 2019年04月09日 08:21:00
 */
public class NewThread {
    /*继承Runable接口*/
    private static class UseRun implements Runnable{
        @Override
        public void run() {
            System.out.println("i am implements Runnable");
        }
    }
    /*继承Callable接口泛型代表 多线程返回值*/
    private static class UseCall implements Callable<String> {
        @Override  /*类似与Runnalble中的run*/
        public String call() throws Exception {
            System.out.println("i am implements Callable");
            return "CallResult";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*启动Runnable接口类型的多线程*/
        UseRun useRun=new UseRun();
//            useRun.run();
        Thread runnableThread=new Thread(useRun);
        runnableThread.start();
        //=================================================================================
        /*启动Callable类型的多线程*/
        UseCall useCall=new UseCall();
        FutureTask<String> futureTask=new FutureTask<>(useCall);
//        useCall.call();
        Thread thread=new Thread(futureTask);
        thread.start();
        //================================================================================
        /*批量启动多个线程*/
        for(int i=0;i<10;i++){
            new Thread(useRun).start();
            new Thread(new FutureTask<>(useCall)).start();
        }
        System.out.println(futureTask.get()+"这是啥");
    }

}
