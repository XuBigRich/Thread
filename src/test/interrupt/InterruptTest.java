package test.interrupt;
/**
 *  1.public static boolean interrupted();// 检测当前线程是否已经中断，此方法会清除中断状态，也就是说，假设当前线程中断状态为true，第一次调此方法，将返回true，表明的确已经中断了，但是第二次调用后，将会返回false，因为第一次调用的操作已将中断状态重新置为false了。
	2.public boolean isInterrupted() ;// 检测当前线程是否已经中断，此方法与上一方法的区别在于此方法不会清除中断状态。
	3.public void interrupt();//将线程中断状态设置为true，表明此线程目前是中断状态。此时如果调用isInterrupted()方法，将会得到true的结果。
	总结 带is的就是当前中断状态
 * @author hongzhi.xu
 * @version 创建时间：2019年11月10日 下午5:12:46
 */
public class InterruptTest {
	    public  static void main(String[] args) throws Exception {
	        System.out.println("初始中断状态：" + Thread.currentThread().isInterrupted());//检查中断状态为false
	        Thread.currentThread().interrupt(); //将线程中断状态设置为true
	        System.out.println("执行完interrupt方法后，中断状态：" + Thread.currentThread().isInterrupted()); //检查当前中断状态为true 
	 
	 
	        System.out.println("首次调用interrupted方法返回结果：" + Thread.currentThread().interrupted());//检查当前中断状态 为true 执行完成后 改变中断 状态 为false
	        System.out.println("此时中断状态：" + Thread.currentThread().isInterrupted()); //检查中断状态为false
	        System.out.println("第二次调用interrupted方法返回结果：" + Thread.currentThread().interrupted()); //设置中断状态为ture 执行完成 后改变中断状态为 false
	        System.out.println("此时中断状态：" + Thread.currentThread().isInterrupted()); //检查中断状态为false
	      
	    }
}
