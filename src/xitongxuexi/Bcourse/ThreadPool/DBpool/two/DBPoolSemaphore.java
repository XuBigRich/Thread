package xitongxuexi.Bcourse.ThreadPool.DBpool.two;

import xitongxuexi.Bcourse.ThreadPool.DBpool.SqlConnectImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 的应用场景和实战
 * 用来控制同时访问特定资源的线程数量，他通过协调各个线程，以保证合理的使用公共资源。
 * 常用于做流量控制，特别是公共资源有限的应用场景，比如说数据库连接池，有界缓存
 */
public class DBPoolSemaphore {
    private final static int POOL_SIZE=10;//连接数量10个
    private static LinkedList<Connection> pool=new LinkedList<>();
    //可用连接  防止线程从连接池中多取
    private final Semaphore useful;
    //已用链接  防止更多数据库连接池放入
    private final Semaphore useLess;
    //初始化数据库连接池
    static{
        for(int i=0;i<POOL_SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }
    public DBPoolSemaphore() {
        this.useful = new Semaphore(POOL_SIZE);
        this.useLess = new Semaphore(0);
    }
    //归还连接
    public void returnConnect(Connection connection) throws InterruptedException {
        if(connection!=null){
            //useful.getQueueLength() 等待获取值的个数     useful.availablePermits()现在的值为
            System.out.println("当前有："+useful.getQueueLength()+"等待数据库链接！！"
                    +"可用连接数："+useful.availablePermits());
            //useLess值-1
            useLess.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            //useful值+1
            useful.release();
    }
}
    //从池子里面拿连接
    public Connection takeConnection() throws InterruptedException {
        //useful值-1
        useful.acquire();
        Connection connection;
        synchronized (pool){
            connection=pool.removeFirst();
        }
        //useLess值+1
        useLess.release();
        return connection;
    }
}
