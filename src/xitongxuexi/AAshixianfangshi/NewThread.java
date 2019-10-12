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
            return "CallResult:"+Thread.currentThread().getName();
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
        /*取值的话 要从FutureTask中取出返回值*/
        FutureTask<String> futureTask=new FutureTask<>(useCall);
//        useCall.call();
        Thread thread=new Thread(futureTask);
        thread.start();
        //================================================================================
        /*批量启动多个线程*/
        for(int i=0;i<10;i++){
            new Thread(useRun).start();
            /*这个地方的FutureTask因为没有引用变量 所以无法被定位到  所以人工取出它的值很困难，
            但是 通过控制台输出的i am implements Callable可以证明其依然被执行了*/
            new Thread(new FutureTask<>(useCall)).start();
        }
        /*当futureTask在线程中执行完毕后，调用futureTask的.get方法可以取出 线程的返回值*/
        System.out.println(futureTask.get()+"这是啥");
    }

}
