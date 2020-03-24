package com.haulmont.testtask.dao;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {

    void add(final T t) throws SQLException;

    List<T> getAll() throws SQLException;

    T getById(final Long id) throws SQLException;

    void update(final T t) throws SQLException;

    void remove(final T t) throws SQLException;

}