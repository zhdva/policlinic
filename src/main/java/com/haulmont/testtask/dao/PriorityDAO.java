package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Priority;

public class PriorityDAO extends BaseDAO<Priority> implements IPriorityDAO {

    public PriorityDAO() {
        super(Priority.class);
    }

}
