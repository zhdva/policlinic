package com.haulmont.testtask.model;

import java.time.LocalDate;
import java.util.*;

public class Prescription {

    private Long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private LocalDate created;
    private LocalDate validity;
    private String priority;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public String getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreated(final LocalDate created) {
        this.created = created;
    }

    public void setValidity(final LocalDate validity) {
        this.validity = validity;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }

    public List<String> getListPriorities() {
        List<String> priorities = new ArrayList<>();
        priorities.add("Нормальный");
        priorities.add("Срочный");
        priorities.add("Немедленный");
        return priorities;
    }

}