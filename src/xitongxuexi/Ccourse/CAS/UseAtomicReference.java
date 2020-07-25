package xitongxuexi.Ccourse.CAS;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author： hongzhi.xu
 * @Date: 2020/6/30 5:22 下午
 * @Version 1.0
 * 类说明：演示引用类型类型的原子操作类
 */
public class UseAtomicReference {
    static AtomicReference<UserInfo> reference = new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo user=new UserInfo("许渣渣",23);
        reference.set(user);
        UserInfo updateUser=new UserInfo("许大富",24);
        //他改变的只是AtomicReference里面对象的引用
        reference.compareAndSet(user,updateUser);
        System.out.println(reference.get().getName());
        //更改了reference后 原对象值其实没有变
        System.out.println(user.getName());
    }
}

class UserInfo {
    String name;
    int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}