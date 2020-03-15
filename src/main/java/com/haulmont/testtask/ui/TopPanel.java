package com.haulmont.testtask.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public class TopPanel {

    private boolean gridAdded = false;

    public static void getMenu(Layout parentLayout) {

        final HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setMargin(true);
        //setContent(hLayout);
        parentLayout.addComponent(hLayout);

        Button buttonPatients = new Button("Пациенты");
        addButtonClickListener(buttonPatients, parentLayout, PatientsGrid.getGrid());
        hLayout.addComponent(buttonPatients);

        Button buttonDoctors = new Button("Врачи");
        //addButtonClickListener(buttonDoctors, parentLayout, DoctorsGrid.getGrid());
        hLayout.addComponent(buttonDoctors);

        Button buttonPrescriptions = new Button("Рецепты");
        //addButtonClickListener(buttonPrescriptions, parentLayout, PrescriptionsGrid.getGrid());
        hLayout.addComponent(buttonPrescriptions);

    }

    private void addButtonClickListener(Button button, Layout parentLayout, Grid newGrid) {

        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                //if (gridAdded) {
                //    parentLayout.replaceComponent(oldGrid, newGrid);
                //} else {
                    parentLayout.addComponent(newGrid);
                    gridAdded = true;
                //}

            }
        });
    }


}
