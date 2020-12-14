package yewushifan.single;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * 单例模式 使用syn实现
 *
 * @Author： hongzhi.xu
 * @Date: 2020/12/14 下午4:26
 * @Version 1.0
 */
public class SingleMode {
    private static SingleMode singleMode;

    private SingleMode() {
    }

    public static SingleMode getInstance() {
//        System.out.println(Thread.currentThread().getName()+"     1");
        //所以Class var = SingleMode.class;这行代码没有意义
        Class var = SingleMode.class;
//        System.out.println(Thread.currentThread().getName()+"     2");
        synchronized (SingleMode.class) {
            SleepTools.ms(10000);
            System.out.println(Thread.currentThread().getName()+"     3");
            if (singleMode == null) {
                singleMode = new SingleMode();
            }
            System.out.println(Thread.currentThread().getName()+"     4");
        }
        return singleMode;
    }
    public static void getInstance2() {
        // synchronized (SingleMode.class) 并不会影响SingleMode.class执行
        Class var = SingleMode.class;
        System.out.println(Thread.currentThread().getName()+"     5");
    }

    public static void getInstance3() {
        // synchronized (SingleMode.class) 是会互斥的
        synchronized (SingleMode.class) {
            SleepTools.ms(10000);
            System.out.println(Thread.currentThread().getName()+"     6");
        }
    }
}
