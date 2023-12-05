package org.example.HW2;

import java.lang.reflect.*;


/**
 Создайте абстрактный класс "Animal" с полями "name" и "age".
 Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
 Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
 Выведите на экран информацию о каждом объекте.
 Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
 */


public class Program {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Animal[] animals = {new Dog("Собака", 4),
                new Cat("Кот", 3)};


        for (Animal animal : animals)
            classInfo(animal);

        System.out.println();
        for (Animal animal : animals)
            methodInvoke(animal, "makeSound");


    }

    private static <T> void classInfo(T obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n")
                .append(modifier(clazz.getModifiers()))  // Метод getModifiers() возвращает результат в виде числа, в котором как бы зашифрованы модификаторы доступа того класса или метода
                .append("class ")
                .append(clazz.getSimpleName()); // Получение названия класса


        if (!clazz.getSuperclass() //Получение суперкласса
                .equals(Object.class)) {

            stringBuilder
                    .append(" extends ")
                    .append(clazz.getSuperclass()
                            .getSimpleName()); //Имя суперкласса
        }

        stringBuilder.append(" {\n");


        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldInfo(field, obj, stringBuilder);
        }

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            stringBuilder
                    .append("\t")
                    .append(modifier(method.getModifiers()))
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName())
                    .append("(");

            Parameter[] parameters = method.getParameters(); // аргументы метода если есть
            if (parameters.length > 0) {
                for (int i = 0; i < parameters.length - 1; i++) {
                    stringBuilder.
                            append(parameters[i].getType().getSimpleName())
                            .append(" ")
                            .append(parameters[i].getName())
                            .append(", ");
                }
            }
            stringBuilder.append(") {}\n");
        }


        stringBuilder.append("}\n");
        System.out.println(stringBuilder.toString());
    }

    private static<T> void fieldInfo(Field field, T obj, StringBuilder stringBuilder) throws IllegalAccessException {
        field.setAccessible(true);  // Разрешаем доступ к закрытому полю
        Object value = field.get(obj);

        if (value.getClass().getSimpleName().equals("String"))
            value = String.format("\"%s\"", value);

        stringBuilder
                .append("\t")
                .append(modifier(field.getModifiers()))   // модификаторы доступа
                .append(field.getType().getSimpleName())  // Возвращает объект класса, который определяет объявленный тип поля, представленного этим объектом Field.
                .append(" ")
                .append(field.getName())
                .append(" = ")
                .append(value)
                .append(";\n");
    }

    private static <T> void methodInvoke(T obj, String methodName) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getName().equals(methodName)) {
                method.invoke(obj);
            }
        }
        System.out.println();
    }


    private static String modifier(int id) {
        return switch (id) {
            case 1 -> "public ";
            case 2 -> "private ";
            case 4 -> "protected ";
            case 8 -> "static ";
            default -> "";
        };

    }


}