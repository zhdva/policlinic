package com.haulmont.testtask.dao;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {

    void add(T t) throws SQLException;

    List<T> getAll() throws SQLException;

    T getById(Long id) throws SQLException;

    void update(T t) throws SQLException;

    void remove(T t) throws SQLException;

}