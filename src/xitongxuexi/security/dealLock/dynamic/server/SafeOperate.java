package xitongxuexi.security.dealLock.dynamic.server;

import xitongxuexi.Acourse.tools.SleepTools;
import xitongxuexi.security.dealLock.dynamic.UserAccount;

/**
 * 线程安全的
 * 保证不发生死锁的一个小诀窍 ，加锁有序化
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/7 6:31 下午
 * @Version 1.0
 */
public class SafeOperate implements ITransfer {
    private static Object tieLock = new Object();//加时赛锁

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        //先锁小，再锁大
        if (toHash < fromHash) {
            synchronized (to) {//先锁转出账户
                System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                SleepTools.second(10);
                synchronized (from) {//再锁转入账户
                    System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                    from.outMoney(amount);
                    to.inMoney(amount);
                }
            }
        } else if (fromHash < toHash) {
            synchronized (from) {//先锁转出账户
                System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                SleepTools.second(10);
                synchronized (to) {//再锁转入账户
                    System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                    from.outMoney(amount);
                    to.inMoney(amount);
                }
            }
        } else {
            //尽量避免进入这个方法，因为在执行这个方法时，其他所有方法都无法继续执行， 又将进行串行 处理。
            //所以前面的各种判断都是为了避免 出现串行处理的问题出现，但是当无法避免时 就要想办法 勇敢面对，（类似于直接给方法加锁）
            synchronized (tieLock) {
                synchronized (from) {//先锁转出账户
                    System.out.println(Thread.currentThread().getName() + " get " + from.getName());
                    SleepTools.second(10);
                    synchronized (to) {//再锁转入账户
                        System.out.println(Thread.currentThread().getName() + " get " + to.getName());
                        from.outMoney(amount);
                        to.inMoney(amount);
                    }
                }
            }
        }

    }
}
