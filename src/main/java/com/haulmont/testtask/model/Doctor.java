package com.haulmont.testtask.model;

public class Doctor extends Person {

    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName().charAt(0) + ". " + getPatronymic().charAt(0) + ". (" + specialization + ")";
    }
}