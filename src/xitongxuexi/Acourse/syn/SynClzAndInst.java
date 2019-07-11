package xitongxuexi.Acourse.syn;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName SynClzAndTnst.java
 * @Description 演示对象锁和类锁
 * @createTime 2019年05月31日 14:12:00
 */
public class SynClzAndInst {
    /*使用类锁*/
    public static class SynClass extends Thread{
        public void run(){
            System.out.println("TestClass is run.....");
            synClass();
        }
    }
    /*使用对象锁线程*/
    public static class InstanceSyn implements Runnable{
        private SynClzAndInst synClzAndInst;
        public InstanceSyn(SynClzAndInst synClzAndInst){
            this.synClzAndInst=synClzAndInst;
        }
        @Override
        public void run() {
            System.out.println("InstanceSyn is run....."+synClzAndInst);
            synClzAndInst.instance();
        }
    }
    /*使用对象锁线程*/
    public static class Instance2Syn implements Runnable{
        private SynClzAndInst synClzAndInst;
        public Instance2Syn(SynClzAndInst synClzAndInst){
            this.synClzAndInst=synClzAndInst;
        }
        @Override
        public void run() {
            System.out.println("Instance2Syn is run....."+synClzAndInst);
            synClzAndInst.instance2();
        }
    }
    public synchronized void instance(){
        SleepTools.second(3);
        System.out.println("sysInstance is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("sysInstance is ended..."+this.toString());
    }
    public synchronized void instance2(){
        SleepTools.second(3);
        System.out.println("sysInstance2 is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("sysInstance2 is ended..."+this.toString());
    }
    public static synchronized void synClass(){
        SleepTools.second(1);
        System.out.println("SynClass is going..."+Thread.currentThread().getName());
        SleepTools.second(1);
        System.out.println("SynClass is ended..."+Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        SynClzAndInst synClzAndInst=new SynClzAndInst();
        Thread t1=new Thread(new InstanceSyn(synClzAndInst));
        SynClzAndInst synClzAndInst2=new SynClzAndInst();
        Thread t2=new Thread(new Instance2Syn(synClzAndInst));
        t1.start();
        t2.start();
        SleepTools.second(1);

        /*展示类锁  类锁与对象锁 互不干扰  但是 static 关键字的 锁 属于类 只有一个 类锁 会相互抢夺 */
        SynClass synClass=new SynClass();
        synClass.start();
        SynClass synClass1=new SynClass();
        synClass1.start();
    }
}
