package xitongxuexi.end;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EndThread.java
 * @Description TODO
 * @createTime 2019年04月09日 11:04:00
 */
public class EndThread {
    private static class UseThread extends Thread{
        public UseThread(String name){
            super(name);
        }
        public void run(){
            String threadName=Thread.currentThread().getName();
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
