package xitongxuexi.Bcourse.notifyAll;
/*
* 类说明 快递实体类
* */
public class Express {
    public final static String CITY="shanghai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/
    public Express(int km,String site){
        this.km=km;
        this.site=site;
    }
    /*变化公里数，然后通知wait状态并需要处理公里数的线程进行业务处理*/
    public synchronized void changeKm(){
        this.km=101;
        notifyAll();
    }
    public synchronized void changeSite(){
        this.site="jinan";
        notifyAll();
        //notifyAll 让出锁 后 会重新 竞争 这个对象锁
        System.out.println("我觉得我还可以抢救一下");
    }
    public synchronized void waitKm(){
        while(km<100){
            try {
                System.out.println("1");
                //如果输出多个1代表 wait执行等待时会让出锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");
        }
        System.out.println("Km业务开始处理");
    }
    public synchronized void waitSite(){
        while(site.equals(Express.CITY)){
            try {
                System.out.println("2");
                //如果输出多个2代表 wait执行等待时会让出锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");
        }
        System.out.println("site业务开始处理");
    }
}
