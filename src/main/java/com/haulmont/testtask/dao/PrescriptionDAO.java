package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.*;
import org.hibernate.Session;

import java.util.List;

public class PrescriptionDAO extends BaseDAO<Prescription> implements IPrescriptionDAO {

    public PrescriptionDAO() {
        super(Prescription.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Prescription> getFilter(final String description, final Long patientId, final Long priorityId) {

        StringBuilder where = new StringBuilder();
        if (description != null) {
            where.append("description LIKE '%").append(description).append("%'");
            if (patientId != null || priorityId != null) {
                where.append(" AND ");
            }
        }
        if (patientId != null) {
            where.append("patient_id = '").append(patientId).append("'");
            if (priorityId != null) {
                where.append(" AND ");
            }
        }
        if (priorityId != null) {
            where.append("priority_id = '").append(priorityId).append("'");
        }

        if (where.toString().isEmpty()) getAll();

        Session session = sf.openSession();
        String query = "from Prescription WHERE " + where;
        List<Prescription> prescriptions = (List<Prescription>) session.createQuery(query).list();
        session.close();

        return prescriptions;

    }

}