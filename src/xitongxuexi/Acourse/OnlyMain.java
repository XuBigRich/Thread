package xitongxuexi.Acourse;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName OnlyMain.java
 * @Description 查看当前运行程序的所有线程   来表达 Java程序天生就是多线程的
 * @createTime 2019年04月09日 07:47:00
 */
public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机管理线程的接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] treadInfo=
                /*获取当前程序中所有线程 false代表不包括锁信息*/
        threadMXBean.dumpAllThreads(false,false);
        for(ThreadInfo threadInfo:treadInfo){
            System.out.println(threadInfo.getThreadName());
        }
    }
}
