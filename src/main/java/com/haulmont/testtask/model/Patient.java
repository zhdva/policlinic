package com.haulmont.testtask.model;

public class Patient extends BaseModel {

    private String name;
    private String surname;
    private String patronymic;
    private String phone;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return surname + " " + name.charAt(0) + ". " + patronymic.charAt(0) + ".";
    }

}