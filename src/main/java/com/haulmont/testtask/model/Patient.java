package com.haulmont.testtask.model;

public class Patient extends Person {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName().charAt(0) + ". " + getPatronymic().charAt(0) + ".";
    }

}