package xitongxuexi.Acourse.syn;
public class SynTest {
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
