package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public class TopPanel {

    private Grid currentGrid;

    public TopPanel(Layout parentLayout) {

        final HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setMargin(true);
        parentLayout.addComponent(hLayout);

        Button buttonPatients = new Button("Пациенты");
        BaseGrid<Patient> patientsGrid = new PatientsGrid();
        addButtonClickListener(buttonPatients, parentLayout, patientsGrid.getGrid());
        hLayout.addComponent(buttonPatients);

        Button buttonDoctors = new Button("Врачи");
        BaseGrid<Doctor> doctorsGrid = new DoctorsGrid();
        addButtonClickListener(buttonDoctors, parentLayout, doctorsGrid.getGrid());
        hLayout.addComponent(buttonDoctors);

        Button buttonPrescriptions = new Button("Рецепты");
        BaseGrid<Prescription> prescriptionGrid = new PrescriptionsGrid();
        addButtonClickListener(buttonPrescriptions, parentLayout, prescriptionGrid.getGrid());
        hLayout.addComponent(buttonPrescriptions);

    }

    private void addButtonClickListener(Button button, Layout parentLayout, Grid newGrid) {

        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (currentGrid != null) {
                    parentLayout.replaceComponent(currentGrid, newGrid);
                } else {
                    parentLayout.addComponent(newGrid);
                }

                currentGrid = newGrid;

            }
        });
    }

}
