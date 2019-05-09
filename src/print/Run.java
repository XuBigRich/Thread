package print;

public class Run {
    public static void main(String[] args) {
    		MyThread run=new MyThread();
            Thread t1=new Thread(run);
            Thread t2=new Thread(run);
            Thread t3=new Thread(run);
            Thread t4=new Thread(run);
            Thread t5=new Thread(run);


            t1.setName("线程1");
            t2.setName("线程2");
            t3.setName("线程3");
            t4.setName("线程4");

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            run.mun();
    }
}
