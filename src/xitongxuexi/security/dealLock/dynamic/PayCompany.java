package xitongxuexi.security.dealLock.dynamic;

import xitongxuexi.security.dealLock.dynamic.server.ITransfer;
import xitongxuexi.security.dealLock.dynamic.server.TransferAccount;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/7 5:46 下午
 * @Version 1.0
 */
public class PayCompany {
    private static class TransferTread extends Thread {
        private String name;
        private UserAccount from;//转出账户
        private UserAccount to;//转入账户
        private int amount;//变动金额
        private ITransfer iTransfer;

        public TransferTread(String name, UserAccount from, UserAccount to, int amount, ITransfer iTransfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.iTransfer = iTransfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            iTransfer.transfer(from, to, amount);
        }
    }

    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount zhangsan = new UserAccount("zhangsan", 20000);
        UserAccount lisi = new UserAccount("lisi", 20000);
        ITransfer iTransfer = new TransferAccount();
        TransferTread zhangsanTolisi = new TransferTread("zhangsanTolisi", zhangsan, lisi, 2000, iTransfer);
        TransferTread lisiTozhangsan = new TransferTread("lisiTozhangsan", lisi, zhangsan, 4000, iTransfer);
        zhangsanTolisi.start();
        lisiTozhangsan.start();
    }
}
