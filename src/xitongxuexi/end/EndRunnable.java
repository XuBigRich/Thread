package xitongxuexi.end;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EndRunable.java
 * @Description TODO
 * @createTime 2019年04月09日 12:10:00
 */
public class EndRunnable {
    private static class UseThread implements Runnable {
        public void run(){
            String threadName=Thread.currentThread().getName();
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(threadName+" is run!");
            }
            System.out.println(threadName+" interrput flag is "+Thread.currentThread().isInterrupted());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread endThread=new Thread(new UseThread());
        endThread.start();
        Thread.sleep(200);
        endThread.interrupt();
    }
}
