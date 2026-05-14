package test.Acourse.isInterrupted;

/**
 * 本示例测试中断wait 与sleep
 * interrupt 作用于线程
 * wait 作用于对象
 */
public class ZhongDuanWait implements Runnable{
    @Override
    public synchronized void run() {
        System.out.println("线程开启成功");
        while(!Thread.interrupted()){
            try {
                System.out.println("wait前"+Thread.interrupted());
                System.out.println("wait前"+Thread.interrupted());
                System.out.println("我开始等待!");
                //wait会中断线程  通过抛出异常改变 中断标识 wait必须写在synchronized关键字方法中  否则会抛出IllegalMonitorStateException
                wait();
//                Thread.sleep(2000);
                System.out.println("我一直在等待!");
            } catch (InterruptedException e) {
                System.out.println("异常后"+Thread.interrupted());
//                这是线程自己在捕获异常后，重新设置自己的中断标志：
                Thread.currentThread().interrupt();
                System.out.println("异常后 执行 Thread.currentThread().interrupt()"+Thread.interrupted());
//试试调用多次输出效果
                Thread.currentThread().interrupt();
                System.out.println("异常后第二次 执行 Thread.currentThread().interrupt()"+Thread.interrupted());
                //其实 Thread.interrupted() 会改变 标志状态，可以尝试再次查询一下状态就会发现已经变为false了，他会返回真实状态不假，但是返回后会改变标志状态
                System.out.println("异常后第三次 执行 Thread.currentThread().interrupt()"+Thread.interrupted());
                e.printStackTrace();
            }
        }
    }
    public synchronized void theNotify(){
        this.notifyAll();
    }
    public static void main(String[] args) throws InterruptedException {
        ZhongDuanWait zhongDuanWait=new ZhongDuanWait();
        Thread thread=new Thread(zhongDuanWait);
        thread.start();
        Thread.sleep(1000);
        //这样不会报错因为 方法被synchronized 包裹着
//        zhongDuanWait.theNotify();
        //这样会报错 因为 main没有 被synchronized 包裹
//        thread.notifyAll();
        //interrupt 会唤醒wait方法  同时 wait方法会抛出InterruptedException 且改变中断标志
        //interrupt 会唤醒sleep 方法  同时 sleep 方法会抛出sleep interrupted
        System.out.println("开始中断");
        thread.interrupt();
    }
}
