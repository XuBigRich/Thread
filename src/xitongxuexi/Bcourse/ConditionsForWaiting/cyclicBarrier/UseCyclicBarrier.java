package xitongxuexi.Bcourse.ConditionsForWaiting.cyclicBarrier;

import xitongxuexi.Acourse.tools.SleepTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * 根据CyclicBarrier 声明计算等待的线程数量
 * 当所有线程的     cyclicBarrier.await(); 数量到达CyclicBarrier 标识的 等待数量时
 * 则启动一个新线程执行 运行声明的线程
 */
public class UseCyclicBarrier {
    //生成CyclicBarrier 实例 当等待线程数到达5个时，会启动一个新的线程 执行
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new CollectThread());
    //jdk提供的线程安全的Map
    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 4; i++) {
            new Thread(new SubThread()).start();
            Thread.sleep(1000);
//            Thread thread=new Thread(new SubThread());
//            thread.start();
        }

    }

    //经行汇总线程
    private static class CollectThread implements Runnable {

        @Override
        public void run() {
            StringBuffer result = new StringBuffer();
            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }
            System.out.println("result=" + result);
            System.out.println("do other business.....");
        }
    }

    private static class SubThread implements Runnable {

        @Override
        public void run() {
            Long id = Thread.currentThread().getId();
            resultMap.put(id + "", id);
            System.out.println("Thread" + id + ".... do sth");
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SleepTools.ms((int) (1000 + id));
            System.out.println("Thread" + id + ".... do my business");
        }
    }
}
