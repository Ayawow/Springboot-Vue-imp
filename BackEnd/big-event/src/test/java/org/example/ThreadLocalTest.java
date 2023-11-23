package org.example;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        //提供一个ThreadLocal对象
        ThreadLocal tl = new ThreadLocal();

        //开启俩个线程
        new Thread(()->{
            tl.set("小颜");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());

            System.out.println(Thread.currentThread().getName() + ":" + tl.get());

            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        },"姜维").start();

        new Thread(()->{
            tl.set("婷儿");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());

            System.out.println(Thread.currentThread().getName() + ":" + tl.get());

            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        },"老师").start();

    }
}
