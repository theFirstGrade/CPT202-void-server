package com.voidserver.controller;

public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            int randomNum = 1000 + (int) (Math.random() * 9000);
            if (randomNum >= 10000){
                System.out.println(randomNum);
            }
        }
//        System.out.println(randomNum);
    }
}
