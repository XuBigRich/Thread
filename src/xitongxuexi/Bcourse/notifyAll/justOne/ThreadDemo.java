package xitongxuexi.Bcourse.notifyAll.justOne;

public class ThreadDemo implements Runnable {
    static int anInt=0;
    private int ofanInt;
    @Override
    public synchronized void run() {
        try {
            anInt++;
            this.ofanInt=anInt;
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程"+ofanInt);
    }
    public synchronized void noty(){
        notifyAll();
    }
}
