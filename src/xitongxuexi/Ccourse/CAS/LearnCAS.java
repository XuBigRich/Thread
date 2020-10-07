package xitongxuexi.Ccourse.CAS;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/6/30 4:05 下午
 * @Version 1.0
 * 什么是原子操作，如何实现
 *      syn是基于阻塞锁的机制，以保证原子操作
 * syn所存在的问题：
 *       1.被阻塞的线程优先级很高
 *       2.拿到锁的线程一直不释放锁怎么办？
 *       3.大量的竞争，消耗cpu，同时造成死锁或者其他安全问题
 *
 *      CAS于是应运而生
 *
 *  CAS的原理：
 *  CAS指令级别保证这是一个原子操作。现在的cpu都支持CAS的指令，他会循环这个指令，直到成功为止
 *  三个运算符：
 *      一个内存地址V,一个期望值A,一个新值B
 *      基本思路：如果 地址V上的值和期望值A相等，就给地址V赋给新值B,如果不是，不做任何操作。
 *      循环（死循环/自旋）里面不断进行CAS操作   （CAS是在硬件层面保证的原子性）
 *  CAS的问题：
 *  ABA问题：
 *      A线程 第一次对地址V进行操作，操作线程取值的时候是A值 改为了B值
 *      B线程 第二次对地址V进行操作，线程取值后又将B值改回了A值
 *      C线程 第三次对地址V进行操作，本来想保证第一次操作A值的原子性，但是这个A 已经被改过两次值了，不再是最初的少年了，所以造成原子性操作的混乱。
 *  解决方案，添加版本号  A1 B2  A3
 *  开销问题：
 *      CAS操作长期不成功，cpu将会不断循环 进行自旋。造成很大的开销问题
 *  只能保证一个共享变量的原子操作：
 *      因为他保证的是一个内存地址的原子操作，而一个内存地址肯定只能存储一个内存变量。所以他有一定局限性，当大批量共享变量在一个方法中需要保证原子操作时，就需要加syn锁了
 */

public class LearnCAS {

}