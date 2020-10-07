package xitongxuexi.security.dealLock.dynamic.server;

import xitongxuexi.Acourse.tools.SleepTools;
import xitongxuexi.security.dealLock.dynamic.UserAccount;

/**
 * 线程不安全
 *
 * 动态死锁，学习完之后有一个疑惑为什么 不直接在transfer方法上添加synchronized 直接添加锁，这样不久可以保证所有的转账都可以保证原子性了吗
 *  经过仔细思考 发现直接锁transfer 有以下缺陷：
 *  1.这是个多线程任务，如果在transfer添加synchronized 造成 这个地方 不管是谁给谁转账都成为了串行，效率低下，无法体现多线程优势。
 *  2.transfer这个方法中 在实际业务里面可能不只是有转账这一个业务，还可能有其他验证。所以，如果直接添加了synchronized 那么其他验证方法也将不可执行
 * 所以尽量将锁的方法范围减小，减小到当前方法这样。
 * 但会出现死锁现象，如： 当两个from 和to 是一个用户时，也就是两个人相互给对方转账时，那么from获得了锁后 ，to就无法获取锁了。
 * 所以可以通过hash算法 将from 与 to 有序化 转账
 * @Author： hongzhi.xu
 * @Date: 2020/10/7 5:40 下午
 * @Version 1.0
 */
public class TransferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) {
        synchronized (from){//先锁转出账户
            System.out.println(Thread.currentThread().getName()+" get "+from.getName());
            SleepTools.second(10);
            synchronized (to){//再锁转入账户
                System.out.println(Thread.currentThread().getName()+" get "+to.getName());
                from.outMoney(amount);
                to.inMoney(amount);
            }
        }
    }
}
