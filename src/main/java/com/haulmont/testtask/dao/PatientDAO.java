package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Patient;

public class PatientDAO extends BaseDAO<Patient> implements IPatientDAO {

    public PatientDAO() {
        super(Patient.class);
    }

}