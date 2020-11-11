package xitongxuexi.Ccourse.CAS;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/6/30 8:18 下午
 * @Version 1.0
 * 带版本戳的原子操作类
 */

public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr=new AtomicStampedReference("BigRich",0);

    public static void main(String[] args) {
        //获取初始的版本号
        final int oldStamp=asr.getStamp();
        final String oldReference=asr.getReference();
        System.out.println("=============================");


    }
}
