package test.Acourse.isInterrupted;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * @Author： hongzhi.xu
 * @Date: 2026/5/14 20:20
 * @Version 1.0
 */
public class Synchronized {
    public String syn = "";
    //对象内共用一个 synchronized。锁的是对象
    public synchronized void a() {
        System.out.println("调用A方法");
        SleepTools.ms(5000);
        System.out.println("调用A方法结束");
    }
    //对象内共用一个 synchronized。锁的是对象
    public synchronized void b() {
        System.out.println("调用B方法");
        SleepTools.ms(5000);
        System.out.println("调用B方法结束");
    }
    //锁的是对象中syn 属性
    public  void c() {
        synchronized (syn) {
            System.out.println("调用C方法");
            SleepTools.ms(5000);
            System.out.println("调用C方法结束");
        }
    }
    //锁的是对象中syn 属性
    public  void d() {
        synchronized (syn) {
            System.out.println("调用D方法");
            SleepTools.ms(5000);
            System.out.println("调用D方法结束");
        }
    }
    //锁的是对象
    public  void e() {
        synchronized (this) {
            System.out.println("调用E方法");
            SleepTools.ms(5000);
            System.out.println("调用E方法结束");
        }
    }

    public static void main(String[] args) {
        Synchronized syn = new Synchronized();
        Thread invokeA = new Thread(syn::a);
        Thread invokeB = new Thread(syn::b);
//        Thread invokeC = new Thread(syn::c);
//        Thread invokeD = new Thread(syn::d);
        Thread invokeE = new Thread(syn::e);
        invokeA.start();
        invokeB.start();
//        invokeC.start();
//        invokeD.start();
        invokeE.start();
        System.out.println("执行完毕");
    }
}
