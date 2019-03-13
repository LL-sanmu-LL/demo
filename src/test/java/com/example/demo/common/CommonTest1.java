package com.example.demo.common;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonTest1 {
    @Test
    public void t1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("javaeqweqw ");
        });
        t1.start();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("end");
    }
    @Test
    public void t2(){
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("2");
        list.add("4");
        list.sort((o1, o2) -> o1.compareTo(o2));
        System.out.println(list);
    }
    @Test
    public void t3(){
        it((o1,o2)-> System.out.println(o1+o2));
    }
    public static interface It01{
        void t1 (int o1, int o2);
    }
    public void it(It01 it){
        int o1=2;
        int o2=4;
        it.t1(o1,o2);
    }
    @Test
    public void t4(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.forEach(System.out::println);
    }
    @Test
    public void t5(){
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }
}

