package xitongxuexi.Acourse.syn;

/**
 * 当threadLocalDemo.t.remove(); 37行 被注释输出
 * start gc
 * end
 * <p>
 * 当threadLocalDemo.t.remove();不被注释
 * 输出：
 * start gc
 * destroy
 * end
 */

class Test {

    byte data[] = new byte[1024 * 1024 * 10];

    @Override
    //重写finalize 当GC触发开始销毁对象时，执行finalize方法
    protected void finalize() throws Throwable {
        System.out.println("destroy");
    }
}

public class ThreadLocalDemo {

    public ThreadLocal<Test> t = new ThreadLocal<>();

    public static void main(String[] args) {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        Test test = new Test();
        threadLocalDemo.t.set(test);
        test = null;

        //ThreadLocal 会引用着当前 线程的线程（Thread）对象
        //Thread对象 有一个ThreadLocalMap 对象
        //ThreadLocal 会调用 Thread对象下的ThreadLocalMap 对象
        //当前ThreadLocal 作为key ，Object 作为value放入 ThreadLocalMap 对象中
        //t.remove 实际上是 清空当前线程对象下 ThreadLocalMap节点 的Entry  的key和value
        //让他们被GC处理
//        threadLocalDemo.t.remove();
        //否者 只清空key （key为弱引用，value 为强引用 ） 。导致当前线程将一直持有value，与线程共存
        threadLocalDemo = null;
        System.out.println("start gc");
        System.gc();
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}


