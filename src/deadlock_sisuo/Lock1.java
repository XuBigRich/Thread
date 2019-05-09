package deadlock_sisuo;

public class Lock1 implements Runnable{
        public void run(){
            try{
                System.out.println("Lock1 running");
                while(true){
                    synchronized(DeadLock.obj1){
                        System.out.println("Lock1 lock obj1");
                        Thread.sleep(3000);//获取obj1后先等一会儿，让Lock2有足够的时间锁住obj2
                        synchronized(DeadLock.obj2){
                            System.out.println("Lock1 lock obj2");
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

}
