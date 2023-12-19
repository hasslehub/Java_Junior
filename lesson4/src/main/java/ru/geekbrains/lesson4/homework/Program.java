package ru.geekbrains.lesson4.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Program {
    public static void main(String[] args) {
        checkConnect();

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {

            // Создание сессии
            Session session = sessionFactory.getCurrentSession();

            // Начало транзакции
            session.beginTransaction();

            // Создание объекта
            Course course = new Course("Java", 12);
            System.out.println("Создан Текущий Курс: " + course);
            session.save(course);
            System.out.println("Текущий Курс сохранен в БД.");

            // Чтение объекта из базы данных
            course = session.get(Course.class, course.getId());
            System.out.println("Текущий Курс извлечен из БД: " + course);

            // Обновление объекта
            course.setTitle("Java Junior");
            course.setDuration(18);
            System.out.println("Данные о Текущем Курсе обновлены: " + course);
            session.update(course);
            System.out.println("Данные о Текущий Курсе обновлены в БД");

            // Чтение объекта из базы данных
            course = session.get(Course.class, course.getId());
            System.out.println("Текущий Курс извлечен из БД: " + course);

            session.delete(course);
            System.out.println("Текущий Курс удален из БД: " + course);

            // Чтение объекта из базы данных
            Course course2 = session.get(Course.class, course.getId());
            System.out.println("Текущий Курс извлечен из БД: " + course2);

            session.getTransaction().commit();

            System.out.println("Текущий Курс остался как объект Java: " + course);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void checkConnect() {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "12345678";

        // Подключение к базе данных
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String readDataSQL = "SHOW DATABASES;";
            try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
                ResultSet resultSet = statement.executeQuery();
                System.out.println("Перечень БД на СУБД сервере:");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
            }

            readDataSQL = "select * from schoolDB.courses";
            try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}