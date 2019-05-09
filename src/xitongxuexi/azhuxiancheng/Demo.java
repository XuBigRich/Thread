package xitongxuexi.azhuxiancheng;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName demo.java
 * @Description TODO
 * @createTime 2019年04月10日 07:33:00
 */
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
    	//	thread.setDaemon(true); // 将此线程设置为守护线程  （同生共死） 需要遵循 设置在 start方法前面
    		thread.start(); // 其是输出的线程名字是thread
            //thread.setPriority(1); // 设置优先级 1-10  缺省 为5   不同操作系统 优先级 不一样   不常用    原理是 时间切片 分配 情况
    	}

}
