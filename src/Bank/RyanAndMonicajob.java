package Bank;

public class RyanAndMonicajob implements Runnable {
    /*
    * 两人轮流查询并取款
    * 查询后 会进入睡眠状态
    * 睡眠后 可进行 取款动作
    * 取款完毕后 会再次查询余额
    *
    * 要求：不可出现透支情况
    *
    * 在不加线程锁的情况下  若要达到此目的  可以通过 判断方法阻止  不过效果没有 线程锁 好。
    * 因为查询 结果 与 取款结果 很可能会出现误差   （取款前查询 余额 100    取款后  余额 80   只取款10元 ）
    * */



    private BankAccount account=new BankAccount();    // 在此初始化后 其 balance 的值将被固定  会随49行 扣减而改变 balance值
    public static int dfcs=0;
    public static int xbcs=0;

    public static void main(String[] args) {
    		RyanAndMonicajob thejob= new RyanAndMonicajob();
            Thread one=new Thread(thejob);
            Thread two=new Thread(thejob);
            one.setName("大富：");
            two.setName("小宝：");
        System.out.println("aab");
            one.start();
        System.out.println("aav");
            two.start();
        System.out.println("aaa");
    }
    @Override
    public void run() {
        for(int x=0;x<10;x++){
//==============================================================================================================================================
            int i;  //这个i是为了 判断 执行了第几次
            if(Thread.currentThread().getName().equals("大富：")){
                dfcs= ++dfcs;
                i=dfcs;
            }else {
                xbcs=  ++xbcs;
                i=xbcs;
            }
//==============================================================================================================================================
            System.out.println(Thread.currentThread().getName()+"的动作"+"  第"+i+"执行");
            makeWithdrawal(10,i);
            System.out.println (Thread.currentThread().getName()+"第 "+i+" 取款后的余额："+account.getBalance());
            if(account.getBalance()<10){
                System.out.println("透支");
            }
        }
    }

    public  void makeWithdrawal(int amout,int i){    //可以通过 在void 前面添加synchronized 字段 来加锁
        if(account.getBalance()>=amout){
            System.out.println(Thread.currentThread().getName()+"第 "+i+" 查询    余额为:"+account.getBalance());
            try{
                System.out.println(Thread.currentThread().getName()+"第 "+i+" 睡觉");
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"第 "+i+"次 开始取款10元");
            account.withdraw(amout);   // 若不加线程锁  这个地方就会 出现透支情况
            System.out.println(Thread.currentThread().getName()+"第 "+i+" 次取款完成");
        }else{
            System.out.println(account.getBalance());
        }

    }
}
