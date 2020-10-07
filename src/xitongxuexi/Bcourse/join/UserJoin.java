package xitongxuexi.Bcourse.join;

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
                thread.join();  //当0这个线程 被主线程插队 后 主线程 被 休眠了 两秒 以至于 后面的 插队 还在进行
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
            //第一次运行创建一个新的线程，但是该线程紧接着被主线程插队，进入等待主线程执行状态
            //第二次与往后的线程，依次被创建并将前一个线程插队到自己前面执行。
            thread.start();
            previous=thread;
        }
        //当所有线程建立完毕后，主线程故意休眠两秒钟，其他线程因为要等待主线程执行完毕后依次执行，所以其他线程也在等待
        System.out.println(Thread.currentThread().getName()+"terminate.");
        SleepTools.second(2);
    }
}
