package test;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName jichu.main.java
 * @Description TODOs
 * @createTime 2019年04月02日 22:37:00
 */
public class main {
    public static void main(String[] args) {
    	Runtest runtest=new Runtest();
        Runtest1 runtest1=new Runtest1();
    	runtest.run();
        runtest1.run();  //事实证明 这样执行run jvm只会把他当作一个普通的方法去执行 不会有线程的出现
        Thread thread=new Thread(runtest);
        Thread thread1=new Thread(runtest1);
        thread.start();
        thread1.start();  // 真正的 线程出现 必须用通过start 去调用 run方法 才会 出现 多线程
    	}
}
