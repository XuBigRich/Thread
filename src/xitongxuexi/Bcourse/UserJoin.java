package xitongxuexi.Bcourse;

import xitongxuexi.Acourse.tools.SleepTools;

public class UserJoin {
    static class JumpQueue implements Runnable{
        private Thread thread;//用来插队的线程
        JumpQueue(Thread thread) {
            this.thread=thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"terminted");
        }
    }

    public static void main(String[] args) {
        Thread previous=Thread.currentThread();//现在是主线程
        for(int i=0;i<10;i++){
            Thread thread=new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName()+"jump a queue the thread:" +thread.getName());
            thread.start();
            previous=thread;
        }
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName()+"terminate.");
    }
}
