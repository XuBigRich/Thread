package xitongxuexi.Ccourse.AQS.readWriteLock;

import xitongxuexi.Acourse.tools.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 3:28 下午
 * @Version 1.0
 */
public class UseRWLock implements GoodsService {

    private GoodInfo goodInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();
    private final Lock setLock = lock.writeLock();

    public UseRWLock(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public GoodInfo getNum() {
        getLock.lock();
        try {
            SleepTools.ms(5);
            return this.goodInfo;
        } finally {
            getLock.unlock();
        }
    }

    @Override
    public void setNum(int number) {
        setLock.lock();
        try {
            SleepTools.ms(5);
            goodInfo.changeNumber(number);
        } finally {
            setLock.unlock();
        }
    }
}
