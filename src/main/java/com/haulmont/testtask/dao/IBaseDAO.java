package com.haulmont.testtask.dao;

import java.util.List;

public interface IBaseDAO<T> {

    T getById(final Long id);
    List<T> getAll();
    void add(final T t);
    void update(final T t);
    void delete(final T t);

}