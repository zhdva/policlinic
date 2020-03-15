package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Patient;

public class PatientsGrid extends BaseGrid<Patient> {

    public PatientsGrid() {

        grid.addColumn(Patient::getSurname).setCaption("Фамилия").setWidth(size);
        grid.addColumn(Patient::getName).setCaption("Имя").setWidth(size);
        grid.addColumn(Patient::getPatronymic).setCaption("Отчество").setWidth(size);
        grid.addColumn(Patient::getPhone).setCaption("Телефон").setWidth(size);

    }

}
