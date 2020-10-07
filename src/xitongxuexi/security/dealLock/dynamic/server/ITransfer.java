package xitongxuexi.security.dealLock.dynamic.server;

import xitongxuexi.security.dealLock.dynamic.UserAccount;

/**
 * 资金转入转出接口
 * @Author： hongzhi.xu
 * @Date: 2020/10/7 5:39 下午
 * @Version 1.0
 */
public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, int amount);
}
