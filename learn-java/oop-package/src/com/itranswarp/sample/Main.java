package com.itranswarp.sample;

import com.itranswarp.world.Person;

public class Main {
    public static void main(String[] args)
    {
        Person ming = new Person("小明",15);
        System.out.println(ming.hello());
    }
}
