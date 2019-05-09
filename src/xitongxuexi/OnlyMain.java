package xitongxuexi;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName OnlyMain.java
 * @Description TODO
 * @createTime 2019年04月09日 07:47:00
 */
public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] treadInfo=
        threadMXBean.dumpAllThreads(false,false);
        for(ThreadInfo threadInfo:treadInfo){
            System.out.println(threadInfo.getThreadName());
        }
    }
}
