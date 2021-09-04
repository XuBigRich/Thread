package yewushifan.weigongxiang;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.misc.Contended;

import java.util.List;

//这个类理论上他所含义的属性一共占用 7*8 =56个字节 （一个缓存行有64个字节可供存储）

/**
 * 一个Java对象包含 8（_mark） +4（oop） +数据
 * 以下面这个对象为例子，虽然对象整体占72个字节，但是 oop和  （_mark）+数据  不是一块连续的字符
 *
 *
 *
 * Java8中已经提供了官方的解决方案，Java8中新增了一个注解：@sun.misc.Contended。
 * 加上这个注解的类会自动补齐缓存行，需要注意的是此注解默认是无效的，需要在jvm启动时设置-XX:-RestrictContended才会生效。
 * 如果加上上面的注解 就可以实现相同的功能
 * 可以加在类上 也可以加在属性上面
 */
//@Contended
public final class VolatileLong {
    //一个long类型占8个字节（byte） ，这是线程主要操作的值
    //对象大小为24字节时（执行时间为18212486500）  为64字节时执行时间（18004912600）
    //若要添加这个字段 需要配置jvm -XX:-RestrictContended
//    @Contended
    public volatile long value = 0L;
    //这个是要被注释掉的  ，他注释与不注释会严重影响程序执行时间
    //之所以会运行的慢是因为 一个缓存行里放下了多个  VolatileLong 对象产生了锁竞争
    //  对象大小为 72时执行时间为（2995817900）    56+8=64,由此这对象正好占有一个内存行
    public long p1, p2, p3, p4, p5, p6;
    //如果到p7 数据部分的长度正好是64位 ，与缓存行长度相同
//    public long p1, p2, p3, p4, p5, p6, p7;



    //在Java中 计算 对象所占字节大小
    public static void main(String[] args) {
        VolatileLong volatileLong = new VolatileLong();
        //64位操作系统 class指针空间大小8字节，MarkWord是8字节  所以只对象头就16字节
        //long占8字节 这里有7个 long 所以占56个字节
        //于是这个对象应该是16+56 在jvm中占72个字节
        //另外在有数组的情况下 会额外增加4个字节描述数组长度，一个int 4个字节 两个int 8个字节 +4个字节描述长度  这个数组占用12个字节 也就是
        long objectSize = ObjectSizeCalculator.getObjectSize(volatileLong);
        System.out.println(objectSize);
    }
}
