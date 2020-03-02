package xitongxuexi.Bcourse.notifyAll.justOne;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<ThreadDemo> threads=new ArrayList<>();
        for(int i=0;i<3;i++){
            ThreadDemo threadDemo=new ThreadDemo();
            Thread thread=new Thread(threadDemo);
            thread.start();
            threads.add(threadDemo);
        }
        threads.get(1).noty();
    }
}
