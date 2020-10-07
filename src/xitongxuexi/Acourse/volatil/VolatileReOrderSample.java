package xitongxuexi.Acourse.volatil;


/**
 * 验证volatile的有序性
 * 关于x与y的最终结果 可能为1,0 也可能为 0,1 也可能为1,1  同样也可能为0,1  同时经过指令重排后也可能会是0，0
 * <p>
 * 但是经过我的测试 ，0，1这种可能性很大可能会出现在第一次执行时， 剩下的数据都将为1,1
 * <p>
 * 我猜测 多年的cpu 优化 造成了这种 指令重排对结果的影响可能性已经降低， 时间片的分割也更佳，造成了 最终的结果
 *
 * @Author： hongzhi.xu
 * @Date: 2020/7/25 1:59 下午
 * @Version 1.0
 */
public class VolatileReOrderSample {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            Thread one = new Thread(() -> {
                a = 1;  //指令重排发生在 线程中， a=1于 x=b执行顺序调个
                x = b;
            });

            Thread other = new Thread(() -> {
                b = 1;
                y = a;

            });
            one.start();
            other.start();
            one.join();  //one.join 会使one线程插队于主线程前面，等one线程执行完毕以后 主线程才会继续往下执行。也就是将原本并行的两个线程 改为了串行
            other.join();   // 作用与上面相同，但是因为开始指令在两个join上面会造成 其实other线程已经执行完毕的情况。所以 两个join在此只是为了保证one线程月other线程执行完毕在主线程之前
            System.out.println("(" + x + "," + y + ")");
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
