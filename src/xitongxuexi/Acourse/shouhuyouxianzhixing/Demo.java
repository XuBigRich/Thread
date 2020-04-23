package xitongxuexi.Acourse.shouhuyouxianzhixing;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName demo.java
 * @Description 线程启动 与 线程守护 与 线程优先级
 * @createTime 2019年04月10日 07:33:00
 */
/*
* 概念--线程守护
*       非守护线程 若主线程跑完了  子线程 没跑完  子线程会继续跑
*       守护线程 若子线程没跑完 而主线程跑完了 那么子线程将会停止 运行
*       将此线程设置为守护线程  （同生共死） 需要遵循 设置在 start方法前面
*       守护线程的try finally不保证一定执行
* 概念--线程启动
*       每个run 都是一个对象
*       每一个线程 都去调用 run这个对象  线程有自己的 内存空间，互不干扰
* 概念--线程优先级
*       设置优先级 1-10  缺省 为5   、
*       不同操作系统 优先级 不一样 ，有的操作系统甚至不存在优先级的概念
*       原理是 时间片 分配 情况 数值越低 分配时间片 越多
*       不常用
* */
public class  Demo {
    public static class Zxc extends Thread {

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName());
            }finally {
                System.out.println("..........finally");
            }
        }
    }
    public static void main(String[] args) {
    		Zxc zxc=new Zxc();
//    		zxc.run();  // 其是输出的线程名字是main
    		Thread thread=new Thread(zxc);
    		/*给这个线程设置一个名字*/
    		thread.setName("zxc");
    		/*是否设置为守护线程*/
        //守护线程必须要在线程启动前 设置
    	    thread.setDaemon(true);
    		thread.start(); // 其是输出的线程名字是zxc
             /* 设置优先级 */
//          thread.setPriority(1);
    	}

}
