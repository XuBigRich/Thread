package xitongxuexi.Ccourse.AQS.readWriteLock;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/10/27 2:49 下午
 * @Version 1.0
 */
public interface GoodsService {
    /**
     * 获取商品的信息
     *
     * @return
     */
    GoodInfo getNum();

    /**
     * 设置商品的数量
     *
     * @param number
     */
    void setNum(int number);
}
