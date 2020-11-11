package xitongxuexi.Ccourse.AQS.interrupted;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用AQS锁进行中断
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/28 3:42 下午
 * @Version 1.0
 */
public class Express {
    public final static String CITY = "shanghai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/
    private Lock lock = new ReentrantLock();
    private Condition kmCondition = lock.newCondition();
    private Condition siteCondition = lock.newCondition();

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /*变化公里数，然后通知wait状态并需要处理公里数的线程进行业务处理*/
    public void changeKm() {
        lock.lock();
        try {
            this.km = 101;
            kmCondition.signal();
        } finally {
            lock.unlock();
        }


    }

    public void changeSite() {
        lock.lock();
        try {
            this.site = "jinan";
            siteCondition.signal();
            System.out.println("我觉得我还可以抢救一下");
        } finally {
            lock.unlock();
        }
    }

    public void waitKm() {
        lock.lock();
        try {
            while (km < 100) {
                try {
                    System.out.println("1");
                    //如果输出多个1代表 await执行等待时会让出锁
                    kmCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1解开了");
            }
            System.out.println("Km业务开始处理");
        } finally {
            lock.unlock();
        }
    }

    public void waitSite() {
        lock.lock();
        try {
            while (site.equals(xitongxuexi.Bcourse.notifyAll.Express.CITY)) {
                try {
                    System.out.println("2");
                    //如果输出多个2代表 await执行等待时会让出锁
                    siteCondition.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2解开了");
            }

            System.out.println("site业务开始处理");

        } finally {
            lock.unlock();
        }
    }
}
