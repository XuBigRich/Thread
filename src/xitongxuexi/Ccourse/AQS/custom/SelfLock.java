package xitongxuexi.Ccourse.AQS.custom;

import javax.security.auth.login.Configuration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 实现一个自己的类ReentrantLock
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/26 2:12 下午
 * @Version 1.0
 */
public class SelfLock implements Lock {
    //state 表示是否获取到锁 当state=1时获取到，state=0时未获取到
    private static class Sync extends AbstractQueuedSynchronizer {
        //是否占用
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new UnsupportedOperationException();
            }
            setExclusiveOwnerThread(null);
            //此处之所以不需要使用cas的方式去设置，是因为独占锁 只有一个线程去释放
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync=new Sync();
    @Override
    public void lock() {
            sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return  sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
