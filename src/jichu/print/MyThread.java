package jichu.print;
public class MyThread extends Thread {

    private int i=5;
    private  int j=5;
    private int n=3;
    public void run(){
//        i=i-1
//        System.out.println("i="+i--+" threadNmae="+Thread.currentThread().getName());  // 造成i输出重复原因 是 抓取后的 i值 输出后 还没来得及-- 就进入了 其他线程
//        System.out.println(n+" "+(n=n-1)+" "+n +Thread.currentThread().getName());    // 输出的 每个 运算 都是 线程随机的  输出 是 统一的
//        try {
//            Thread.sleep(1111);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("j="+--j+" threadNmae="+Thread.currentThread().getName());

    }

    public void mun(){
//        System.out.println(i--+" "+i);  //他会先输出5后再进行 --活动    输出 5 4
//          System.out.println(--i+" "+i);  //他会先--活动 赋值给i 输出   并第二次再输出4        输出 4 4
    }
    /*
    * 个人理解i++与++i
    * 他会先抓取i的值
    *    然后i++ 就先 以抓取的值输出i  然后再++； ++i 就 以抓取值为标准进行++ 然后 输出i
    * 抓取动作 与 后续动作线程 分离  受线程安全影响
    *
    *
    *
    * */
}
