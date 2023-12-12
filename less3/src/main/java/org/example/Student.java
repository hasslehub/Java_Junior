package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student implements Serializable {

    private String name;

    private int age;

    private transient double GPA;  // Поле не подлежит сериализации

    public Student() {

    }

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGPA() {
        return GPA;
    }
/*
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(age);
        out.writeObject(GPA);
    }





    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = (int) in.readObject();
        GPA = (double) in.readObject();
    }
*/
    @Override
    public String toString() {
        return String.format("Student{name='%s', age=%d, GPA=%.2f}", name, age, GPA);

    }
}
