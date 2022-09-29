package com.example.springdubboconsumer.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// https://blog.csdn.net/yongbutingxide/article/details/122213350  具体可以看这篇文章
public class Test {
    public static void main(String[] args) {
        //接口Queue继承 --> 集合接口Collection
        //阻塞队列类型的接口BlockingQueue继承 --> 接口Queue
        //ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue、DelayQueue、PriorityBlockingQueue 和 LinkedTransferQueue六种阻塞队列的实现类 --> 实现了BlockingQueue接口
        //ArrayBlockingQueue是一种有界队列，内部是使用数组存储元素，利用ReentrantLock来实现线程安全
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10, false);//arg1: 容量(不能扩容)  arg2: 是否公平
        try {
            arrayBlockingQueue.put("666");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //LinkedBlockingQueue是一种无界队列，内部是使用链表存储元素，不设置初始容量的话，默认容量就是Integer.MAX_VALUE
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        // ------------ 第一组类型 -------------
        linkedBlockingQueue.add("zhong");
        linkedBlockingQueue.add("jun");
        linkedBlockingQueue.remove("zhong");
        String element = linkedBlockingQueue.element();

        // ------------- 第二组类型 -------------
        //添加一个元素
        boolean wang = linkedBlockingQueue.offer("wang");
        //移除头元素，并返回被移除的元素
        String poll = linkedBlockingQueue.poll();
        //返回队列的头元素
        String peek = linkedBlockingQueue.peek();

        // ------------- 第三组类型 ---------------
        try {
            //添加元素，满了就阻塞，这里是用到Condition来实现线程的阻塞、唤醒
            linkedBlockingQueue.put("zhang");
            //删除队列的头元素，并返回元素
            String take = linkedBlockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(linkedBlockingQueue);

        //总的来说就是用到ReentrantLock来保证队列的线程安全问题，用Condition来实现队列满了 和 队列空了后 线程的阻塞、唤醒

    }
}
