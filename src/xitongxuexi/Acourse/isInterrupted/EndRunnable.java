package xitongxuexi.Acourse.isInterrupted;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EndRunable.java
 *
 * 继承了Runnable的类 无法直接使用isInterrupterd 所以需要使用Thread.currentThread（）获取当前线程  再执行isInterrupted()
 * Thread.currentThread().isInterrupted()
 *
 * @Description 线程协作停止  主线程休眠200毫秒  线程 自动进行死循环
 *          随后 主线程苏醒 并发出interrupt指令后  thread线程接收到
 *          thread停止循环运行 继续向下执行 直到线程结束
 * @createTime 2019年04月09日 12:10:00
 */
public class EndRunnable {
    private static class UseThread implements Runnable {
        public void run(){
            String threadName=Thread.currentThread().getName();
            /*isInterrupted()是Thread类下的一个方法 如果是继承了Runnable 需要用 Thread.currentThread().isInterrupted()  */
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(threadName+" is run!");
            }
            System.out.println(threadName+" interrput flag is "+Thread.currentThread().isInterrupted());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread endThread=new Thread(new UseThread());
        endThread.start();
        Thread.sleep(2000);
        endThread.interrupt();
    }
}
