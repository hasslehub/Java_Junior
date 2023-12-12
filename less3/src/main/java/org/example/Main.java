package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student = new Student();
        List<Student> students = new ArrayList<>();

        students.add(new Student("Владимир", 18, 4.55));
        students.add(new Student("Василий", 17, 3.98));
        students.add(new Student("Максим", 21, 4.52));


        //Сериализация
        try(
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("student.dat"))){
            objectOutputStream.writeObject(student);
            System.out.println("Объект Student сериализован.");

        }

        //Десериализация
        try(
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("student.dat"))){
            student = (Student) objectInputStream.readObject();
            System.out.println("Объект Student десериализован.");
        }

        System.out.println("----------");
        System.out.println("Объект Student десериализован.");
        System.out.println("Имя: " + student.getName());
        System.out.println("Возраст: " + student.getAge());
        System.out.println("Средний балл (должен быть 0.0, так как transient): " + student.getGPA());



        // JSON
        //Сериализация json
        System.out.println();
        System.out.println("Сериализация с JSON");
        List<Student> studentJSON = new ArrayList<>();
        try {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File("student.json"),students);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Десериализация с JSON");
        //Десериализация json
        if (new File("student.json").exists()) {
            try {
                studentJSON.add(objectMapper
                        .readValue("student.json", objectMapper.getTypeFactory()
                                .constructType(Student.class)));
            } catch (JsonProcessingException e) {
                System.err.println(e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
        System.out.println(studentJSON);


        //  XML
        //Сериализация xml
        System.out.println();
        System.out.println("Сериализация с XML");
        List<Student> studentXML = new ArrayList<>();;
        try {
            xmlMapper.writeValue(new File("student.xml"), students);
        } catch (IOException e){
            e.printStackTrace();
        }
        //Десериализация xml
        System.out.println();
        System.out.println("Десериализация с XML");
        if (new File("student.xml\"").exists()) {
            try {
                studentXML = xmlMapper
                        .readValue("student.xml", xmlMapper.getTypeFactory()
                                .constructType(Student.class));
            } catch (JsonProcessingException e) {
                System.err.println(e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
        System.out.println(studentXML);

    }

}