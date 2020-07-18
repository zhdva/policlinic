package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Doctor;

public class DoctorDAO extends BaseDAO<Doctor> implements IDoctorDAO {

    public DoctorDAO() {
        super(Doctor.class);
    }

}
