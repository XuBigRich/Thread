package xitongxuexi.Ccourse.AQS.readWriteLock;

import xitongxuexi.Acourse.tools.SleepTools;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 2:53 下午
 * @Version 1.0
 */
public class UseSyn implements GoodsService {

    private GoodInfo goodInfo;

    public UseSyn(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public synchronized GoodInfo getNum() {
        SleepTools.ms(5);
        return goodInfo;
    }

    @Override
    public synchronized void setNum(int number) {
        SleepTools.ms(5);
        goodInfo.changeNumber(number);
    }

}
