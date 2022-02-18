package test.threadNum;

import xitongxuexi.Bcourse.ConditionsForWaiting.countDownLatch.UseContDownLatch;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.*;


/**
 * 测试线程数对 处理速度的影响
 * 200个工作
 * 计算密集型
 * 16个线程处理200个工作   --- 计算密集型
 * 200个线程处理200个工作  --- 计算密集型
 * <p>
 * i/o密集型
 * 16个线程处理200个工作  ---  i/o密集型
 * 200个线程处理200个工作   ---  i/o密集型
 * <p>
 * 小计算量 ，小io时  ，使用16个线程的处理快
 * 大计算量 ，大io时， 使用200个工作线程快
 * <p>
 * 16个线程在运行io时，因为存在io比cpu处理速度慢，所以cpu存在io等待，于是cpu不回满负荷运行，
 * 在执行io时 也需要进行cpu的介入（cpu进行io的指令下发），但是cpu指令下发快于 io执行，所以存在io等待，正是因为io等待 所以cpu无法一直处于运行状态
 * 我们可以 多添加 线程，因为io操作可以 独自完成，不需要cpu参与（cpu只是下达i/o指令），所以cpu中 某一线程执行i/o等待时，可以切换cpu执行权给其他线程
 *
 * @Author： hongzhi.xu
 * @Date: 2022/2/16 7:21 下午
 * @Version 1.0
 */
public class TestThreadNum {

    public static class IOWork implements Runnable {
        CountDownLatch ioContDown;
        int i;

        public IOWork(CountDownLatch ioContDown, int i) {
            this.i = i;
            this.ioContDown = ioContDown;
        }

        @Override
        public void run() {
            String s = "hahahhhhhhhhh";
            try {
//                Files.write(Paths.get("/Users/xuhongzhi/Desktop/filetest"), s.getBytes(StandardCharsets.UTF_8));
                Files.copy(Paths.get("/Users/xuhongzhi/Downloads/WeCom_3.1.19.90358.dmg"), Paths.get("/Users/xuhongzhi/Desktop/test/WebStorm-2021.3.dmg" + i), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("完成");
            if (ioContDown != null) {
                ioContDown.countDown();
            }

        }
    }

    public static class ComputingWork implements Runnable {
        CountDownLatch computingContDown;

        public ComputingWork(CountDownLatch computingContDown) {
            this.computingContDown = computingContDown;
        }

        @Override
        public void run() {
            Long g = 2l;
            //小数据量计算
//            for (long i = 0l; i < 0x7ffl; i++) {
            //大数据量计算
            for (long i = 0l; i < 0x7ffffffl; i++) {
                g = g * 3;
            }
//            System.out.println(g);
            if (computingContDown != null) {
                computingContDown.countDown();
            }

        }
    }

    public static class CountWork implements Runnable {
        CountDownLatch computingContDown;
        int i = 0;

        public CountWork(CountDownLatch computingContDown, int i) {
            this.i = i;
            this.computingContDown = computingContDown;
        }

        @Override
        public void run() {
            Long g = 2l;
            //小数据量计算
//            for (long i = 0l; i < 0x7ffl; i++) {
            //大数据量计算

            for (long f = 0l; f < 0x7ffffffl; f++) {
                g = g * 3;
                try {
                    Files.copy(Paths.get("/Users/xuhongzhi/Downloads/WeCom_3.1.19.90358.dmg"), Paths.get("/Users/xuhongzhi/Desktop/test/WebStorm-2021.3.dmg" + i), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            System.out.println("完成1");
            if (computingContDown != null) {
                computingContDown.countDown();
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {
        int theadNum = 200;
        //小线程池
        Executor litter = new ThreadPoolExecutor(16, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());


        //大线程池
        Executor great = new ThreadPoolExecutor(theadNum, theadNum,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        Long a = System.currentTimeMillis();
        //-- 大线程池计算
        CountDownLatch greatComputing = new CountDownLatch(theadNum);
        for (int i = 0; i <= theadNum; i++) {
            great.execute(new ComputingWork(greatComputing));
        }
        greatComputing.await();
        Long b = System.currentTimeMillis();
        System.out.println("大线程池执行计算" + (b - a));
        // --小线程池计算
        CountDownLatch litterComputing = new CountDownLatch(theadNum);
        for (int i = 0; i <= theadNum; i++) {
            litter.execute(new ComputingWork(litterComputing));
        }
        litterComputing.await();
        Long c = System.currentTimeMillis();
        System.out.println("小线程池执行计算" + (c - b));
        // -- 大线程池i/0
        CountDownLatch greatIo = new CountDownLatch(theadNum);
        for (int i = 0; i <= theadNum; i++) {
            great.execute(new IOWork(greatIo, i));
        }
        greatIo.await();
        Long d = System.currentTimeMillis();
        System.out.println("大线程池执行io" + (d - c));
        //    --   小线程池i/0
        CountDownLatch litterIo = new CountDownLatch(theadNum);
        for (int i = theadNum; i <= theadNum * 2; i++) {
            litter.execute(new IOWork(litterIo, i));
        }
        litterIo.await();
        Long e = System.currentTimeMillis();
        System.out.println("小线程池执行io" + (e - d));
        // --综合计算
        CountDownLatch count = new CountDownLatch(theadNum);
        for (int i = theadNum; i <= theadNum * 2; i++) {
            great.execute(new CountWork(count, i));
        }
        count.await();
        Long f = System.currentTimeMillis();

        System.out.println("大线程池执行io与计算" + (f - e));

//        for (int i = theadNum; i <= theadNum * 2; i++) {
//            new ComputingWork(null).run();
//        }
//        Long g = System.currentTimeMillis();
//        System.out.println("单线程执行io" + (g - f));
////        System.out.println("单线程执行计算" + (f - e));
//        for (int i = theadNum; i <= theadNum * 2; i++) {
//            new IOWork(null, i).run();
//        }
//        Long h = System.currentTimeMillis();
//
//        System.out.println("单线程池执行io" + (h - e));

    }

}
