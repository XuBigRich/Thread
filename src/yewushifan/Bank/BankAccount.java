package yewushifan.Bank;

public class BankAccount {
    private  int balance=100;
    public int getBalance(){
        return balance;
    }
    public void withdraw(int amout){
//        if (balance-amout<0){
//            System.out.println("余额不足");    // 部分情况下 可以通过 判断达到 想要的效果 ，但是  无法替代线程锁 的功能
//        }else {
            balance = balance - amout;
//        }
    }
}
