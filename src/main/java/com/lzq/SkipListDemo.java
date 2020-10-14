package com.lzq;

import java.util.Random;

public class SkipListDemo {


    public static void main(String[] args) {

        SkipList l = new SkipList();
        Random r = new Random();
        for (int i = 0; i < 10; i++ )
        {
            int tmp = r.nextInt(100);
            System.out.println("add:"+tmp);
            l.put( tmp,  tmp );
            l.printHorizontal();
        }

        System.out.println("over");

    }
}
