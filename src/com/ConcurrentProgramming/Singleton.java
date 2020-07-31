package com.ConcurrentProgramming;

//单例模式
public class Singleton {
    private static volatile Singleton s;
    private Singleton () {
    }
    public static Singleton getInstance() {
        if (s == null) {
            synchronized (Singleton.class) {
                if (s == null) {
                    //由于volatile的存在能够确保指令不发生重排序
                    s = new Singleton();
                }
            }
        }
        return s;
    }
}

class Singleton2 {
    private static Singleton2 s;
    //私有化构造器，确保唯一实例
    private Singleton2 () {

    }
    public static Singleton2 getInstance() {
        //这种单例模式的实现会导致多个线程同时
        //判断s == null，然后初始化了多个Instance对象
        if (s == null) {
            s =  new Singleton2();
        }
        return s;
    }
}

class Singleton3 {
    private static Singleton3 s;
    //私有构造器
    private Singleton3 () {

    }
    //通过同步确保单次只有一个线程对对象进行初始化
    //但是每次获取示例都需要加锁 开销大大
    public static synchronized Singleton3 getInstance() {
        if (s == null) {
            s = new Singleton3();
        }
        return s;
    }
}

class Singleton4 {
    private static Singleton4 s;
    private Singleton4 () {

    }
    public static Singleton4 getInstance() {
        if (s == null) {
            //锁住实例类对象
            synchronized (Singleton4.class) {
                if (s == null) {
                    /**
                     * 1.分配内存空间
                     * 2.执行初始化
                     * 3.内存空间指针赋值到引用对象
                     * 指令重排序会将第三步提前到第二部执行
                     * 这样其他线程可能会访问到一个未被初始化但不为null的值
                     * */
                    s = new Singleton4();
                }
            }
        }
        return s;
    }
}

//基于类初始化的方案
class Singleton5 {

    private Singleton5 () {}
    //声明一个静态内部类,这个类中持有一个单例对象
    //当未出发内部类的加载初始化时，这个对象是不存在的。
    //如果由线程要访问这个类的对象，会触发这个静态内部类的初始化
    //jvm底层加锁确保只有一个进程完成初始化操作，其他线程等待
    /**
     * java规定对于每一个类和接口对有一个唯一的初始化锁LC对应
     * 从C到LC的映射由JVM实现，jvm在类初始化阶段会尝试获取这个锁
     * 相应的多个线程就不会发生冲突
     * */
    private static class SingletonHolder {
        public static Singleton5 s = new Singleton5();
    }
    public static Singleton5 getInstance() {
        return SingletonHolder.s;
    }
}