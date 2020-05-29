package xitongxuexi.Acourse.syn;

public class UseThreadLocal {
    //ThreadLocal 的作用是保存每一个线程 的唯一变量
    //   也就是说，每一个线程通过threadLocal.get() 方法获取到的值是这个线程唯一的；与其他线程不同
    static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer> (){
        @Override
        protected Integer initialValue(){
            return 1;
        }
    };
    public void startThreadArray(){
        Thread[] runs=new Thread[3];
        for(int i=0;i<runs.length;i++){
            runs[i]=new Thread(new TestThread(i));
        }
        for(int i=0;i<runs.length;i++){
            runs[i].start();
        }
    }
    public static class TestThread implements Runnable{
        int id;
        public TestThread(Integer id){
            this.id=id;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"start");
            Integer s=threadLocal.get();
            s=s+id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            /*内存回收方法*/
//            threadLocal.remove();
            //此方法用于测试remove的作用，建议每次使用完ThreadLocal 就remove 以节省内存
            System.out.println(Thread.currentThread().getName()+"再次确认是否还有值:"+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal=new UseThreadLocal();
        useThreadLocal.startThreadArray();
    }
}
