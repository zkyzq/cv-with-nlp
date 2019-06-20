package com.itranswarp.world;

public class Person {
    int age;
    String name;

    public Person()
    {

    }
    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public String hello()
    {
        return "Hello, " + this.name;
    }

}
