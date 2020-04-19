package xitongxuexi.Bcourse.exchange;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 用于线程间的数据交换，他提供一个同步点，在同步点
 * 两个线程可以交换彼此数据
 * 例如：第一个线程执行了Exchange 那么它将等待第二个线程执行Exchange
 * 当两个线程都执行了Exchange后 ，那么就可以进行数据交换了 ，将各自的信息传达给对方
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            Set<String> setA = new HashSet<>();//存放数据容器
            try {
                /*添加数据*/
                setA.add("许大富");
                setA = exchanger.exchange(setA);
                /*处理交换后的数据*/
                System.out.println(setA.size());
                Iterator iterator = setA.iterator();
                while (iterator.hasNext()) {
                    System.out.println("setA:" + iterator.next());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            Set<String> setB = new HashSet<>();//存放数据容器
            try {
                /*添加数据*/
                setB.add("许渣渣");
                setB = exchanger.exchange(setB);
                /*处理交换后的数据*/
                System.out.println(setB.size());
                Iterator iterator = setB.iterator();
                while (iterator.hasNext()) {
                    System.out.println("setB:" + iterator.next());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
