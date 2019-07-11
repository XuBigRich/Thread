package xitongxuexi.Acourse.tools;

import java.util.concurrent.TimeUnit;

public class SleepTools {
    /*按秒休眠*/
    public static final void second(int second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
        }
    }
    /*
    * 按照毫秒休眠*/
    public static final void ms(int second){
        try {
            TimeUnit.MILLISECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
