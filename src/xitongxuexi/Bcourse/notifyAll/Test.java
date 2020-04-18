package xitongxuexi.Bcourse.notifyAll;

import jichu.main;
import sun.security.action.GetBooleanAction;

/**
 * 类说明：
 * 	wait 与notifyAll
 * 	wait :调用前持有锁，调用wait方法后 锁会被立即释放
 * 	notifyAll:	调用前持有锁，  而释放锁 必须是在包含notifyAll方法执行完成后 才会释放锁
 */
public class Test {
	public synchronized void notify1() {
		notifyAll();
	}
public static void main(String[] args) throws InterruptedException {
	Thread aaThread=new Thread(Notify.get());
	aaThread.start();
	Test test =new Test();
//	test.test();
	Thread.sleep(1000);
	Notify.get().notify1();
}

}
 class  Notify implements Runnable {
	 private static Notify notify=new Notify();
	 private Notify () {}
	public static Notify get() {
		return notify;
	}
	 @Override
	 public synchronized void run() {
		 System.out.println(Thread.currentThread().getName()+"进入等待");
	 		try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 System.out.println(Thread.currentThread().getName()+"释放等待");
	 }
	public synchronized void notify1() {
			notifyAll();
	}
}

