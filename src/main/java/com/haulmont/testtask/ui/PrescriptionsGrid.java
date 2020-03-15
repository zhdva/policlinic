package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Prescription;

public class PrescriptionsGrid extends BaseGrid<Prescription> {

    public PrescriptionsGrid() {

        grid.addColumn(Prescription::getDescription).setCaption("Описание").setWidth(400);
        grid.addColumn(Prescription::getPatient).setCaption("Пациент").setWidth(size);
        grid.addColumn(Prescription::getDoctor).setCaption("Врач").setWidth(size);
        grid.addColumn(Prescription::getDateCreate).setCaption("Дата создания").setWidth(150);
        grid.addColumn(Prescription::getValidity).setCaption("Срок действия").setWidth(150);
        grid.addColumn(Prescription::getPriority).setCaption("Приоритет").setWidth(150);

    }

}