package com.voidserver.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Test {

    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 100000; i++) {
//            int randomNum = 1000 + (int) (Math.random() * 9000);
//            if (randomNum >= 10000){
//                System.out.println(randomNum);
//            }
//        }
//        System.out.println(randomNum);

//        LocalDateTime t = LocalDateTime.now();
//        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
//        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        System.out.println(t);
//        System.out.println(milliSecond);
//        System.out.println(second);

//        String path = "C:\\Users\\年级第一\\Desktop\\productImages";
//        File file1 = new File(path + "\\" + String.valueOf(milliSecond) + "屏幕截图(3).png");
//        if (!file1.exists()) {
//            file1.createNewFile();
//        }

//        String a = "C:\\Users\\年级第一\\Desktop\\productImages\\1619642823950屏幕截图(10).png";
//        System.out.println(a.replaceAll("\\\\","/"));


        String file = "屏幕截图(1).png";
        String[] split = file.split("\\.");
        System.out.println(split.length);

    }
}
