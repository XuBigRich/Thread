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
                a = 1;
                x = b;
            });

            Thread other = new Thread(() -> {
                b = 1;
                y = a;

            });
            one.start();
            other.start();
            one.join();
            other.join();
            System.out.println("(" + x + "," + y + ")");
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
