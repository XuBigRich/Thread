package test;

import java.util.ArrayList;
import java.util.List;

public class ThisDemo {
    static class Man{
        int old;
        String name;
        public Man(int old,String name){
            this.name=name;
            this.old=old;
        }
        public void push(List object){
            object.add(this);
        }
    }

    public static void main(String[] args) {
        List<Man> list=new ArrayList();
        Man man=new Man(22,"许鸿志");
        man.push(list);
        System.out.println(list.get(0).old);
    }
}
