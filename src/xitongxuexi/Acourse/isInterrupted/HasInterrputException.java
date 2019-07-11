package xitongxuexi.Acourse.isInterrupted;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName HasInterrputException.java
 * @Description isInterrupted即使在线程阻塞状态下 也能第一时间接收到 终止 命令  来终止线程的 运行
 *                  但是会抛出异常
 * @createTime 2019年04月10日 17:22:00
 */
public class HasInterrputException {
    private static class UseThread extends Thread{
        public UseThread(String name){
            super(name);
        }
        public void run (){
            String threadName= Thread.currentThread().getName();
            while (!isInterrupted()){
                try {
                    System.out.println(threadName+" interrput flag is "+isInterrupted());
                    Thread.sleep(2000);  //之所以会报错 是因 为他在运行睡眠过程中 收到了 中断指令
                } catch (InterruptedException e) {  //这个异常被抛出后 interrupt会被复位
                    interrupt();
                    System.out.println(threadName+" interrput flag is "+isInterrupted()+" in Exception!");
                    e.printStackTrace();
                }
            }
                    System.out.println(threadName+" interrput flag is "+isInterrupted());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        UseThread thread=new UseThread("HasInterrputEx");
        thread.start();
        Thread.sleep(200);
        thread.interrupt();
    }
}
