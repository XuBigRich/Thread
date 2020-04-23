package xitongxuexi.Acourse.isInterrupted;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EndThread.java
 * Thead 的 isInterrupterd()方法
 * @Description 线程协作停止  主线程休眠200毫秒  线程 自动进行死循环
 *          随后 主线程苏醒 并发出interrupt指令后  thread线程接收到
 *          thread停止循环运行 继续向下执行 直到线程结束
 * @createTime 2019年04月09日 11:04:00
 */
public class EndThread {
    private static class UseThread extends Thread{
        public UseThread(String name){
            super(name);
        }
        public void run(){
            String threadName=Thread.currentThread().getName();
            /*isInterrupted默认是false 中断*/
            while(!isInterrupted()){
                System.out.println(threadName+" is run!");
            }
            System.out.println(threadName+" interrput flag is "+isInterrupted());
        }
    }
    public static void main(String[] args) throws InterruptedException {
    		Thread endThread=new UseThread("endThread");
    		endThread.start();
    		Thread.sleep(200);
            endThread.interrupt();
    	}
}
