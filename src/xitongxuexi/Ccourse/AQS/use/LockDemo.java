package xitongxuexi.Ccourse.AQS.use;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/26 4:51 下午
 * @Version 1.0
 * 使用显式锁的标准范式
 */
public class LockDemo {
    private Lock lock=new ReentrantLock();
    private int count;
    public  void increament(){
        lock.lock();
        try {
            //确保业务逻辑如果抛出异常 锁仍然可以被释放
            count++;
        }finally {
            lock.unlock();
        }
    }

    public  synchronized void sync(){
        count++;
    }

}
