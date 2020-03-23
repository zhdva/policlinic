package com.haulmont.testtask.model;

public class Doctor extends BaseModel {

    private String name;
    private String surname;
    private String patronymic;
    private String specialization;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return surname + " " + name.charAt(0) + ". " + patronymic.charAt(0) + ". (" + specialization + ")";
    }
}