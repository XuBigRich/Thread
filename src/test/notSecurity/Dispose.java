package test.notSecurity;

import java.util.HashMap;
import java.util.Map;
/**
 * 一开始：
 *      怀疑 所有的线程安全问题都存在于 同一个对象 同一个方法操作 属性时出现线程安全问题
 * 后来学习发现：
 * 根据后来两天的学习，了解到，jvm中内存区域分为程独占区域，与线程共享区域。
 * 方法属于线程独占区域，方法中的 临时变量储存在栈中，
 * 静态变量    位于方法区
 * 实例变量    作为对象的一部分，保存在堆中。
 * 临时变量    保存于栈中，栈随线程的创建而被分配。
 *
 * 所以多线程操作一个对象的方法，会根据不同线程分配不同的栈空间，每个线程的栈空间的临时变量存储这些 传入方法的参数
 * 各自独立 所以并不会出现线程 安全的问题。
 *
 * 而出现线程安全问题的 地方是在线程共享区的堆内存中，因为在 栈空间存储对象属性的地方，实例变量会保存到堆栈中，而堆栈是
 * 线程共享区，一起操作会导致线程安全问题。
 * 另一个出现线程安全的问题在 cpu 高速缓存上，这个在小绿本上有详细记录 "许鸿志猜想"
 */
public class Dispose {
    public Map<String, String> tager = new HashMap();
    public int i=2;
    public void addMap(String id, String values) throws InterruptedException {
        String key = id;
        Double timed= Math.random()*10000;
        Long time=timed.longValue();
        System.out.println(time);
        Thread.sleep(time);
        String value = values;
        tager.put(key, value);
    }

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = new HashMap();
        map.put(1, "1");
        map.put(2, "1");
        Map<Integer, String> map2 = new HashMap();
        map2.put(1, "2");
        map2.put(2, "2");
        Map<Integer, String> map3 = new HashMap();
        map3.put(1, "3");
        map3.put(2, "3");
        Map<Integer, String> map4 = new HashMap();
        map4.put(1, "4");
        map4.put(2, "4");
        Dispose dispose = new Dispose();
        Handler handler = new Handler(dispose, map);
        Thread thread = new Thread(handler);
        Handler handler2 = new Handler(dispose, map2);
        Thread thread2 = new Thread(handler2);
        Handler handler3 = new Handler(dispose, map3);
        Thread thread3 = new Thread(handler3);
        Handler handler4 = new Handler(dispose, map4);
        Thread thread4 = new Thread(handler4);
        thread.start();
        Handler.latch.countDown();
        thread2.start();
        Handler.latch.countDown();
        thread3.start();
        Handler.latch.countDown();
        thread4.start();
        Handler.latch.countDown();
        Thread.sleep(10000);
        for (String id : dispose.tager.keySet()) {
            System.out.println(id+" " +dispose.tager.get(id));
        }
    }
}
