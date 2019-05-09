package xunhuan;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadTest.java
 * @Description TODO
 * @createTime 2019年04月02日 18:40:00
 */
public class ThreadTest {
    public static void main(String[] args) {
    		new ThreadTest().init();
    }
    public void init(){
        final Business business=new Business();
        new Thread(()->{
            for(int i=0;i<50;i++){
                business.SubThread(i);
            }}).start();
        for(int i=0;i<50;i++){
            business.MainThread(i);
        }
    }

private class Business{
        boolean bshouldSub=true; //这里相当于定义了控制谁该执行的一个信号灯
    public synchronized void MainThread(int i){
        if(bshouldSub){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int j=0;j<10;j++) {
            System.out.println(Thread.currentThread().getName());
        }
        bshouldSub=true;
        this.notify();
    }
        public synchronized void SubThread(int i){
            if(!bshouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        for(int j=0;j<10;j++) {
            System.out.println(Thread.currentThread().getName());
        }
        bshouldSub=false;
        this.notify();
        }
    }
}
