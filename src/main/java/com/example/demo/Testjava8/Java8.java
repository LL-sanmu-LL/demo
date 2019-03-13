package com.example.demo.Testjava8;

public class Java8 implements Intfa {
    public static void main(String[] args) {
        Java8 j = new Java8();
        j.methodA();
        Intfa.methodB();
        Intfa.methodC();
    }
}

interface Intfa {
    default void methodA(){
        System.out.println(123);
    }
    static void methodB(){
        System.out.println(321);
    }
    public static void methodC(){
        System.out.println(222);
    }
}