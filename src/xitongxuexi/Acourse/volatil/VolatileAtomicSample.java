package xitongxuexi.Acourse.volatil;

/**
 * 验证volatile是否保证原子性
 *
 * @Author： hongzhi.xu
 * @Date: 2020/7/25 1:33 下午
 * @Version 1.0
 */
public class VolatileAtomicSample {
    private static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        /**
         * 这个程序计算出来的counter 是小于等于10000的  或许为9787 或许为8457 或许为10000
         */
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter++;
                }
            });
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println(counter);
    }
}
