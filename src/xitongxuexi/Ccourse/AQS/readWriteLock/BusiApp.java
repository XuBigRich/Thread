package xitongxuexi.Ccourse.AQS.readWriteLock;

import xitongxuexi.Acourse.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 *AQS读写锁
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 3:05 下午
 * @Version 1.0
 */
public class BusiApp {
    static final int readWriteRatio = 10;
    static final int minThreadCount = 3;
    static CountDownLatch latch = new CountDownLatch(readWriteRatio * minThreadCount);




    private static class GetThread implements Runnable {
        private GoodsService goodsService;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            notify();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            //每获取一百次 执行一次打印 单线程执行UseSyn的getNum()方法
            // 多线程执行     goodsService.getNum();方法
            //排队进入getNum()方法
            for (int i = 0; i < 100; i++) {
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + "读取商品耗时："
                    + (System.currentTimeMillis() - start) + "ms");
        }
    }

    private static class SetThread implements Runnable {
        private GoodsService goodsService;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long start = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                SleepTools.ms(50);
                goodsService.setNum(random.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName() + "写入商品数据耗时："
                    + (System.currentTimeMillis() - start) + "ms------");
        }
    }

    public static void main(String[] args) {
        GoodInfo goodInfo = new GoodInfo("Cup", 10000, 10000);
        //该测试方法 前期会出现大量空闲无打印状态，因为多线程访问goodsService时，里面的synchronized关键字 强制他们成了单线程 排队执行了
//        GoodsService goodsService = new UseSyn(goodInfo);
        //经过读写锁的优化，只有在写时是单线程，读时 变成多线程
        GoodsService goodsService = new UseRWLock(goodInfo);
        for (int i = 0; i < minThreadCount; i++) {
            Thread set = new Thread(new SetThread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                Thread get = new Thread(new GetThread(goodsService));
                get.start();
                latch.countDown();
            }
            set.start();
            latch.countDown();
        }

    }
}
