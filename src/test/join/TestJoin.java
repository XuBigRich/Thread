package test.join;

import xitongxuexi.Acourse.tools.SleepTools;

public class TestJoin {
    static class Join1 implements Runnable{

        @Override
        public void run() {
            System.out.println("join11111运行");
            System.out.println("join11111运行结束");
        }
    }

    static class Join2 implements Runnable{
        private Thread thread;
        public Join2(Thread thread){
            this.thread=thread;
        }
        @Override
        public void run() {
            System.out.println("join22222运行");
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("join22222运行结束");
        }
    }
    public static void main(String[] args){
        Join1 join1=new Join1();
        Thread thread=new Thread(join1);
        Join2 join2=new Join2(thread);
        Thread thread2=new Thread(join2);
        thread2.start();
        thread.start();
    }
}
