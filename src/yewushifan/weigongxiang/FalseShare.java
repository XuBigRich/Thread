package yewushifan.weigongxiang;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class FalseShare implements Runnable {
    //初始化被声明的变量
    private final int arrayIndex;
    //控制数组大小
    private final static int NUM_THREADS = 4; // change
    //在线程中参与逻辑预算 （静态）
    private final static long ITERATIONS = 500L * 1000L * 1000L;
    // 多线程共同操作的数组
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        //在静态代码块中赋值（初始化）他们要共同操作的数组
        for (int i = 0; i < longs.length; i++) {
            //给共同操作的VolatileLong 类型的 long数组， 添加数组元素，因为是四个线程操作 所以就给他添加四个 数组元素就可以
            //注意 这个数组上面会有四个VolatileLong对象，每个VolatileLong对象占有72个字节  56（数据长度）+16（对象头长度）
            longs[i] = new VolatileLong();
        }
    }


    //在执行线程时使用的 线程变量
    public FalseShare(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public void run() {
        //将要对VolatileLong 的value属性 进行更改的值
        long i = ITERATIONS + 1;
        while (0 != --i) {
            //对数组上的第arrayIndex个值进行更改，一共有四个数组，第一个线程更改数组第一个值、第二个线程更改数组第二个值、第三个线程更改数组第三个值、第四个线程更改数组第四个值
//            longs[arrayIndex].value = i;
        }
    }
}