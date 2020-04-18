package xitongxuexi.Bcourse.notifyAll.justOne;

import static java.lang.Thread.sleep;

public class ThreadDemo implements Runnable {
    private static Integer anInt = 0;
    private int ofanInt;

    @Override
    public synchronized void run() {
        //如果不锁住anInt 可能会出现 两个线程 所用的anInt值相同的情况
        // synchronized只能保证当前线程操作的当前对象的锁 不会被其他线程抢去（属于对象锁）
        //而变量是不可以添加synchronized 关键字的
        //所以 需要建立一个类方法（静态方法） 由synchronized 关键字描述
        //（补充）如果 想要在操作某个方法时 不想让别的方法 再去操作这个方法 那么就需要锁住这个方法 某一个方法块
        try {
            //这样表示在执行方法块中的方法时，任何对象不可以执行getAnInt()方法
            synchronized (getAnInt()) {
                Integer objectInteger = getAnInt();
                sleep(200);  //此方法可以验证 synchronized (getAnInt()) 对排斥其他线程操作getAnInt（）起到的作用
                anInt = ++objectInteger;
                this.ofanInt = anInt;
                System.out.println(anInt);
            }
            System.out.println("我开始执行" + ofanInt);
            wait();
            System.out.println("我执行完毕" + ofanInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void noty() {
        notifyAll();
    }

    public static synchronized Integer getAnInt() {
        return anInt;
    }
}
