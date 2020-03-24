package com.haulmont.testtask.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCcontroller {

    private static final String dbDriver = "org.hsqldb.jdbc.JDBCDriver";
    private static final String dbUrl = "jdbc:hsqldb:file:./db/policlinicdb";
    private static final String dbUsername = "SA";
    private static final String dbPassword = "";
    protected Connection connection;

    public Connection getConnection() {

        try {

            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("База данных подключена");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        }

        return connection;

    }

}