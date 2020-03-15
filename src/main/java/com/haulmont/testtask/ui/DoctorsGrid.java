package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Doctor;

public class DoctorsGrid extends BaseGrid<Doctor> {

    public DoctorsGrid() {

        grid.addColumn(Doctor::getSurname).setCaption("Фамилия").setWidth(size);
        grid.addColumn(Doctor::getName).setCaption("Имя").setWidth(size);
        grid.addColumn(Doctor::getPatronymic).setCaption("Отчество").setWidth(size);
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(size);

    }

}