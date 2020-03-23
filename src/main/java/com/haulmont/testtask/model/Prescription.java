package com.haulmont.testtask.model;

import java.util.Date;

public class Prescription extends BaseModel {

    private String description;
    private Patient patient;
    private Doctor doctor;
    private Date created;
    private Date validity;
    private String priority;

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getCreated() {
        return created;
    }

    public Date getValidity() {
        return validity;
    }

    public String getPriority() {
        return priority;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(final Doctor doctor) {
        this.doctor = doctor;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public void setValidity(final Date validity) {
        this.validity = validity;
    }

    public void setPriority(final PrescriptionPriority priority) {
        this.priority = priority.toString();
    }

}