package com.youngfeng.kotlin;

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-05-31 11:18
 */
public class Client {

    public interface R2 {
        void foo();
    }

    public static void main(String[] args) {
        Person person = new Person("Scott Smith", 30);
        person.setName("Peter");
        System.out.println(person.getName());

        person.isFemale = true;

//        person.setFemale(true);
//        System.out.println(person.isFemale());

        // 访问全局函数
        JavaInterop.foo();
        Util.f1();
        Util.f2();

        Tank.Companion.create();
        Tank.MOVED = true;

        Singleton.provider = new Provider();
        boolean created = Singleton.CREATED;

        Tank tank = new Tank();
        tank.setName(null);
    }
}
