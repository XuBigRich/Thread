package piciqidong;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadTest.java
 * @Description TODO
 * @createTime 2019年04月11日 21:56:00
 */
public class ThreadTest {
    private int j;
    public static void main(String[] args) {
    		ThreadTest tt=new ThreadTest();
    		Inc inc=new Inc();
    		Dec dec=new Dec();
    		for (int i=0;i<2;i++){
                Thread t=new Thread(inc); //因为 Thread里面 是 一个对象 ，这样 循环 启动 其实只是同一个线程启动了两次
                t.start();
                 t=new Thread(dec);
                 t.start();
            }
    }
    static class Inc implements Runnable{
        Integer a = 0;
        @Override
        public void run() {
           synchronized (a){

                System.out.println("Inc运行"+a++);
            }
        }
    }
    static class Dec implements Runnable{
        Integer b = 0;
        @Override
        public void run() {
            synchronized (b) {
                System.out.println("Dec运行"+b++);
            }
        }
        }
    }

