package test.notSecurity;

import java.util.HashMap;
import java.util.Map;
/**
 * 目前怀疑 所有的线程安全问题都存在于 同一个对象 同一个方法操作 属性时出错的
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
