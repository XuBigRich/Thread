package xitongxuexi.security.dealLock.dynamic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动态死锁
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/7 5:28 下午
 * @Version 1.0
 */
public class UserAccount {
    private final String name;
    private int money;
    private final Lock lock = new ReentrantLock();

    public UserAccount(String name, int amount) {
        this.name = name;
        this.money = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return money;
    }


    //转入资金
    public void inMoney(int amount) {
        money = money + amount;
    }

    //转出资金
    public void outMoney(int amount) {
        money = money - amount;
    }

}
