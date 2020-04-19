package xitongxuexi.Bcourse.ThreadPool.DBpool.one;

import xitongxuexi.Bcourse.ThreadPool.DBpool.SqlConnectImpl;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池  ‘里面存着很多数据库连接对象’
 */
public class DBPool {
    private static LinkedList<Connection> pool=new LinkedList<>();
    public DBPool(int initalSize){
        if(initalSize>0){
            for(int i=0;i<initalSize;i++){
                pool.addLast(SqlConnectImpl.fetchConnection());  //通过sqlConnectImpl方法产生一个 数据库连接对象  并放入 pool容器中
            }
        }
    }

    /**
     * 从数据库连接池中取出一个数据库连接 对象
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConn(int mills) throws InterruptedException {
        synchronized (pool){
            if(mills<0){
                while (pool.isEmpty()){             //如果数据库连接池空了
                 pool.wait();                       //等待
                }
                return pool.removeFirst();           //从数据库连接池中取出第一个连接对象  并在 连接池中删除这个连接对象
            }else {
                long overtime=System.currentTimeMillis()+mills;
                long remain=mills;
                if(pool.isEmpty()&&remain>0) {       //如果数据库连接池为空且超时时间大于零 就等待
                    pool.wait(remain);              //等待时间remain
                    remain=overtime-System.currentTimeMillis();
                }
                Connection result=null;
                if(!pool.isEmpty()){                 //如果数据库连接池 为空
                    result=pool.removeFirst();      //从数据库连接池中取出第一个连接对象  并在 连接池中删除这个连接对象
                }
                return result;
            }
        }
    }
    /*放回数据库连接*/
    public void releaseConn(Connection conn){
        if(conn!=null){
            synchronized (pool){
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }
}
