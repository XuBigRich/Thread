package xitongxuexi.Ccourse.AQS.reentrant;

/**
 * 可重入锁的概念 就是递归调用本身的方法
 * <p>
 * 同时这也是一个非常有趣的递归函数
 *
 * 锁的可重入的概念就是
 *  1。如果锁定了自己方法 ，依然可以递归调用自己     如test1
 *  2。如果本方法有锁，他依然可以调用其他带锁的方法   如test2
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 8:59 上午
 * @Version 1.0
 */
public class UseReentrantLock {
    public synchronized int test(int i) {
        int b = i + 1;
        while (b > 10) {
            return b;
        }
        int a = test(b);
        return a;
//         b = test(b)+b;
//        return b;
    }


    public synchronized int test2() {
        return test(1);
    }

    public static void main(String[] args) {
        UseReentrantLock useReentrantLock = new UseReentrantLock();
        int i = useReentrantLock.test2();
        System.out.println(i);
    }

}
