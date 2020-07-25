package xitongxuexi.Ccourse.CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/6/30 5:14 下午
 * @Version 1.0
 * 学习对AtomicInteger 的使用
 */
public class UseAtomicInteger {
    static AtomicInteger ai=new AtomicInteger(10);

    public static void main(String[] args) {
        //先取旧值然后值加1   i++
        System.out.println(ai.getAndIncrement());
        //先加1 然后再将值取出  ++i
        System.out.println(ai.incrementAndGet());
        //直接取值
        System.out.println(ai.get() );
    }
}
