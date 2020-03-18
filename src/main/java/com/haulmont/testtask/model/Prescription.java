package com.haulmont.testtask.model;

import java.util.Date;

public class Prescription extends BaseModel {

    private String description;
    private String patient;
    private String doctor;
    private Date dateCreate;
    private Date validity;
    private String priority;

    public String getDescription() {
        return description;
    }

    public String getPatient() {
        return patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public Date getDateCreate() {
        return dateCreate;
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

    public void setPatient(final String patient) {
        this.patient = patient;
    }

    public void setDoctor(final String doctor) {
        this.doctor = doctor;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setValidity(final Date validity) {
        this.validity = validity;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }


    private enum Priority {
        normal, cito, statim;
    }

}