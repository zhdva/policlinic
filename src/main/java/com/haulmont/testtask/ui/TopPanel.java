package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

import java.sql.SQLException;

public class TopPanel {

    private Layout currentGrid;

    private Layout parentLayout;

    public TopPanel(final Layout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public HorizontalLayout getTopButtons() throws SQLException {

        final HorizontalLayout topButtons = new HorizontalLayout();
        topButtons.setMargin(true);

        Button buttonPatients = new Button("Пациенты");
        buttonPatients.setWidth("200");
        BaseGrid<Patient> patientsGrid = new PatientsGrid();
        addButtonClickListener(buttonPatients, parentLayout, patientsGrid);

        Button buttonDoctors = new Button("Врачи");
        buttonDoctors.setWidth("200");
        BaseGrid<Doctor> doctorsGrid = new DoctorsGrid();
        addButtonClickListener(buttonDoctors, parentLayout, doctorsGrid);

        Button buttonPrescriptions = new Button("Рецепты");
        buttonPrescriptions.setWidth("200");
        BaseGrid<Prescription> prescriptionGrid = new PrescriptionsGrid();
        addButtonClickListener(buttonPrescriptions, parentLayout, prescriptionGrid);

        topButtons.addComponent(buttonPatients);
        topButtons.addComponent(buttonDoctors);
        topButtons.addComponent(buttonPrescriptions);

        return topButtons;

    }

    private void addButtonClickListener(final Button button,final Layout parentLayout, final BaseGrid bg) {

        button.addClickListener(event -> {
            Layout newLayout = bg.getButtonsAndGrid();
            if (currentGrid != null) {
                parentLayout.replaceComponent(currentGrid, newLayout);
            } else {
                parentLayout.addComponent(newLayout);
            }
            currentGrid = newLayout;
        });

    }

}
