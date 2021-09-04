package test.printABC;

/**
 * 按照顺序输出AAABBBCCC，让线程有序执行
 *
 * @author 许鸿志
 * @since 2021/8/25
 */
public class JoinAAABBBCCC {
    Thread thread;
    Thread thread1;
    Thread thread2;

    public JoinAAABBBCCC() {
        thread = new Thread(runnable);
        thread1 = new Thread(runnable1);
        thread2 = new Thread(runnable2);
    }

    Runnable runnable = () -> {
        for (int i = 0; i < 10; i++) {
            System.out.println("A");
        }
    };
    Runnable runnable1 = () -> {
        try {
            //thread 插队到 thread1前面
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("B");
        }
    };

    Runnable runnable2 = () -> {
        try {
            //thread1 插队到 thread2前面
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("C");
        }
    };

    public static void main(String[] args) throws InterruptedException {
        JoinAAABBBCCC joinAAABBBCCC = new JoinAAABBBCCC();
        //thread插队到主线程前面
        joinAAABBBCCC.thread.join();

        joinAAABBBCCC.thread2.start();
        joinAAABBBCCC.thread1.start();
        joinAAABBBCCC.thread.start();
    }
}
