package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Patient;
import com.vaadin.ui.Grid;

public class PatientsGrid  {

    private Grid<Patient> pGrid = new Grid<>();

    public PatientsGrid() {

        pGrid.addColumn(Patient::getSurname).setCaption("Фамилия");
        pGrid.addColumn(Patient::getName).setCaption("Имя");
        pGrid.addColumn(Patient::getPatronymic).setCaption("Отчество");
        pGrid.addColumn(Patient::getPhone).setCaption("Телефон");

    }

    public Grid<Patient> getGrid() {
        return pGrid;
    }

}
