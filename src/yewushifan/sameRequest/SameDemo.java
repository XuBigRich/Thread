package yewushifan.sameRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此demo 是为了 让相同参数的请求执行同一对象方法时，线程安全
 *
 * @Author： hongzhi.xu
 * @Date: 2022/2/7 10:02 下午
 * @Version 1.0
 */
public class SameDemo {
    /**
     * 使用Map模拟数据库
     */
    public Map database = new HashMap();

    /**
     * 模拟向数据库中填充数据
     */ {
        database.put("1", "我是更改前的数据1");
        database.put("2", "我是更改前的数据2");
        database.put("3", "我是更改前的数据3");
    }

    /**
     * 更新或添加接口
     *
     * @param id
     * @param message
     */
    public void updateAndSave(String id, String message) {
        System.out.println(id + "  :开始");
        //有则更新，无则添加
        database.put(id, message);
        System.out.println(id + "  :结束");
    }

    /**
     * 1. 原版
     * 常规的更新 效率低下，直接食用synchronize 锁对象
     *
     * @param id
     */
    public void baseUpdate(String id, String message) {
        synchronized (this) {
            updateAndSave(id, message);
        }
    }

    /**
     * 2.使用字符串常量池
     * 放入字符串常量池
     * 常量池类似于hashmap存在 ，默认大小1009 （jdk8 60013），当数据越大时，hash冲突越严重。练表数据越长
     * 所以这样会影响效率，所以不推荐这样做
     *
     * @param id
     * @param message
     */
    public void constantPool(String id, String message) {
        synchronized (id.intern()) {
            updateAndSave(id, message);
        }
    }

    /**
     * 3.使用ConcurrentHashMap
     * 利用hashMap的特性，他们的key去做对比，每个key都会生成唯一的对象 用于上锁.
     * 但是这样会有一个问题（bug）：
     * 当多个线程同时到达synchronized 时，获取到相同对象，只有一个线程可以获取到这个对象的锁。
     * 当这个获取到对象锁的线程执行完毕后， 会执行chm.remove(id); 对象移除操作。
     * 再次进来的 线程，发现 原来的key 已经没有要锁的对象 就会新生成一个
     * 这样 新进来的对象 与之前 的对象 虽然时一个key  但是 是不同的object  这样就会出现线程安全问题
     * 解决方案：
     * 我认为，可以直接不移除 id，让id永久保存，但这样 会造成hashmap越来越大。
     */
    public Map chmObject = new ConcurrentHashMap();

    public void HashMapObject(String id, String message) {
        Object o = chmObject.computeIfAbsent(id, k -> new Object());
        synchronized (o) {
            updateAndSave(id, message);
            chmObject.remove(id);
        }
    }

    /**
     * 4. 使用ConcurrentHashMap
     * 依然利用hashmap特性，但是他这次存储一个显示锁，而不单纯存一个对象了。
     */
    public Map<String, ReentrantLock> chmAccording = new ConcurrentHashMap();

    public void HashMapAccording(String id, String message) {
        ReentrantLock rl = chmAccording.computeIfAbsent(id, k -> new ReentrantLock());
        rl.lock();
        updateAndSave(id, message);
        //判断等待获取锁的 队列长度，如果等于0，那么就可以将锁移除掉了
        if (rl.getQueueLength() == 0) {
            chmAccording.remove(id);
        }
        rl.unlock();
    }

    /**
     * 5.以封装的思想 去实现单一请求的线程安全
     */
    class Encapsulation {
        public Map<String, ReentrantLock> chmAccording = new ConcurrentHashMap();

        public void exec(String id, Runnable statement) {
            ReentrantLock rl = chmAccording.computeIfAbsent(id, k -> new ReentrantLock());
            rl.lock();
            statement.run();
            //判断等待获取锁的 队列长度，如果等于0，那么就可以将锁移除掉了
            if (rl.getQueueLength() == 0) {
                chmAccording.remove(id);
            }
            rl.unlock();
        }
    }

    public void encapsulation(String id, String message) {
        Encapsulation encapsulation = new Encapsulation();
        encapsulation.exec(id, () -> updateAndSave(id, message));
    }

    public static void main(String[] args) {
        SameDemo sameDemo = new SameDemo();
        ThreadPoolExecutor executors = new ThreadPoolExecutor(200, 1000, 20, TimeUnit.HOURS, new ArrayBlockingQueue(200));
        for (int i = 0; i <= 1000; i++) {
            int finalI = i;
//            executors.execute(()->sameDemo.baseUpdate("1",new String(String.valueOf(finalI))));
//            executors.execute(()->sameDemo.constantPool("1",new String(String.valueOf(finalI))));
            //线程不安全的错误案例
//            executors.execute(()->sameDemo.HashMapObject("1",new String(String.valueOf(finalI))));
//            executors.execute(()->sameDemo.HashMapAccording("1",new String(String.valueOf(finalI))));
            executors.execute(()->sameDemo.encapsulation("1",new String(String.valueOf(finalI))));
        }
    }
}
