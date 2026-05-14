package test.exception.threadpool.countDownAndException;

public class ResultThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我先执行");
            //这个异常 只有在主线程调用get 的时候才可以被主线程捕获到，否则 即使报错了 主线程也不会被感知也不会抛出
            int i = 1 / 0;

        }
    }