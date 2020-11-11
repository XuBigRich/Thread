package xitongxuexi.Ccourse.AQS.readWriteLock;

/**
 * 读写锁的使用
 *
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 2:39 下午
 * @Version 1.0
 */
public class GoodInfo {
    /**
     * 商品名字
     */
    private final String name;
    /**
     * 总销售额
     */
    private double totalMoney;
    /**
     * 总库存
     */
    private int storeNumber;

    public GoodInfo(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void changeNumber(int sellNumber) {
        this.totalMoney += sellNumber * 25;
        this.storeNumber -= sellNumber;
    }
}
