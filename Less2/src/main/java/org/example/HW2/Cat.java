package org.example.HW2;

public class Cat extends Animal {

    public Cat(String name, int age) {
        super(name, age);
    }


    @Override
    public void makeSound() {
        System.out.printf("%s мяукает\n", name);
    }

    @Override
    public String toString() {
        return "Cat " + name;
    }
}
