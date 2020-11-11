package xitongxuexi.security.performance;

/**
 * 关于性能方面：
 * 仔细观察下面两个方法的不同
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/8 10:34 上午
 * @Version 1.0
 */
public class Test implements Runnable {
    private int result;
    private int a;
    private int b;

    public Test(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 此方法锁的是 方法 不管任何值进入方法都将串行 计算效率低下 多线程受到很大束缚
     *
     * @param a
     * @param b
     * @return
     */
    public synchronized int calc(int a, int b) {
        result = a * b;
        return result;
    }

    /**
     * 此方法锁的是 方法行 计算时可以多线程计算，但赋值时将进行串行赋值，大大提高了计算效率。
     * 要知道计算是一个耗时的任务，如果可以多线程 那么将大大提高效率。
     * 而赋值耗时很短。所以仅在赋值时上锁，保证了他的运行效率。
     *
     * @param a
     * @param b
     * @return
     */
    public int calc2(int a, int b) {
        int temp = a * b;
        synchronized (this) {
            result = temp;
            return result;
        }
    }

    @Override
    public void run() {
        int result = calc(this.a, this.b);
        System.out.println(result);
        int result1 = calc2(this.a, this.b);
        System.out.println(result1);
    }

    public static void main(String[] args) {
        Test test = new Test(200, 1);
        Thread thread = new Thread(test);
        thread.start();

    }
}
