package xitongxuexi.Acourse.syn;
public class SynTest {
    //这个关键字的意义是每次读写值都直接刷新到内存当中，不保存于高速缓存区
    private volatile int age=100000;
    private static class TestThread extends Thread{
        private SynTest synTest;
        public TestThread(SynTest synTest,String name){
            super(name);
            this.synTest=synTest;
        }
        public void run(){
            for(int i=0;i<100000;i++){
                synTest.test();
            }
            System.out.println(Thread.currentThread().getName()+"age="+synTest.getAge());
        }
    }
    public void test(){
        age++;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
