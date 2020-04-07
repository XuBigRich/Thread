package xitongxuexi.Bcourse.notifyAll.justOne;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * 不管是执行notify还是int
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<ThreadDemo> threads=new ArrayList<>();
        for(int i=0;i<3;i++){
            ThreadDemo threadDemo=new ThreadDemo();
            Thread thread=new Thread(threadDemo);
            thread.start();
            threads.add(threadDemo);
        }
        //有时候会执行notify失败 是因为线程没有启动完成 就执行了notify方法(所以需要等待2秒让线程全部启动完毕)
        sleep(2000);
        System.out.println("执行notify");
        threads.get(1).noty();
        System.out.println("执行完毕");
    }
}
