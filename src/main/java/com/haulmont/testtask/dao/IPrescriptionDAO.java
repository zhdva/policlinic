package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Prescription;

import java.util.List;

public interface IPrescriptionDAO extends IBaseDAO<Prescription> {

    public List<Prescription> getFilter(final String description, final Long patientId, final Long priorityId);

}
