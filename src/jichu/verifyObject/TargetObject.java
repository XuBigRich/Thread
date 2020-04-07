package jichu.verifyObject;

import static java.lang.Thread.sleep;

/**
 * 本类方法实验目的，用同一个对象开启多线程 ，与不同对象开启多线程的区别
 * 结论：
 *  1.当线程开启的 对象是同一个对象时，那么 所有线程操作 均 影响于同一个对象
 *  2.当线程开启的 对象时不同对象时，那么 所有线程操作的 对象 均为不同对象
 */
public class TargetObject implements Runnable {
    private int targetParameter =0;
    @Override
    public void run() {
        this.targetParameter=++targetParameter;
        System.out.println("输出现有值"+targetParameter);
        System.out.println("如果输出的hashCode一样则代表操作的对象一样： "+this.hashCode());
    }

    public static void main(String[] args) throws InterruptedException {
        int i=0,g=0;
        TargetObject onlyObject=new TargetObject();
        while(i<3){
            Thread onlyObjectThread=new Thread(onlyObject);
            onlyObjectThread.start();
            i++;
            sleep(200);
            //判定每次run方法内的this.targetParameter=++targetParameter
            //是否对所有线程操作的对象造成了影响
            System.out.println(onlyObject.targetParameter);
        }
        System.out.println("结果就是onlyObject对象的 targetParameter属性 出现了累加，确定了 多个线程 的确处理的是同一个对象");
        System.out.println("==================下面是对照试验====================================");
      while (g<3){
            TargetObject multiObject=new TargetObject();
            Thread multiObjectThread=new Thread(multiObject);
            multiObjectThread.start();
            g++;
            //他大概率可能会赶在run方法运行之前执行 ，那么将会出现连续的三个0
             //解决方案为让当前线程休眠一定时间等 其他线程执行完毕后，再让主线程继续
            System.out.println(multiObject.targetParameter);
        }
    }
}
