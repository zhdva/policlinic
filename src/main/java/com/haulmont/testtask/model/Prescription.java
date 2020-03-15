package com.haulmont.testtask.model;

import java.util.Date;

public class Prescription {

    private String description;
    private String patient;
    private String doctor;
    private Date dateCreate;
    private String validity;
    private String priority;

    public Prescription(String description,
                        String patient,
                        String doctor,
                        Date dateCreate,
                        String validity,
                        String priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.dateCreate = dateCreate;
        this.validity = validity;
        this.priority = priority;
    }

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

    public String getValidity() {
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

    public void setValidity(final String validity) {
        this.validity = validity;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }


    private enum Priority {
        normal, cito, statim;
    }

}