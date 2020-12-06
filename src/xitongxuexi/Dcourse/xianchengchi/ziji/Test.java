package xitongxuexi.Dcourse.xianchengchi.ziji;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/12/3 下午2:46
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        MyThreadPool myThreadPool=new MyThreadPool();
        myThreadPool.execute(()-> System.out.println("嘿嘿"));
        SleepTools.ms(1000);
        myThreadPool.destroy();
    }
}
