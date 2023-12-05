package org.example.HW2;

public class Dog extends Animal{

    public Dog(String name, int age) {
        super(name, age);
    }


    @Override
    public void makeSound() {
        System.out.printf("%s лает", name);
    }

    @Override
    public String toString() {
        return "Dog " + name;
    }
}
