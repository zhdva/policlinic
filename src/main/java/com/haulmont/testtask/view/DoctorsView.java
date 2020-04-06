package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.dao.PrescriptionController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorsView extends BaseView<Doctor> {

    public DoctorsView() throws SQLException {
        setController(new DoctorController());
        setGrid(getDoctorsGrid());
        SingleSelect<Doctor> selection = getGrid().asSingleSelect();
        setSelection(selection);
        setAddWindow("Добавить врача");
        setEditWindow("Редактирование врача");
        HorizontalLayout buttons = getButtons("400");
        buttons.addComponent(statisticsButton());
        VerticalLayout buttonsAndGrid = getButtonsAndGrid();
        buttonsAndGrid.addComponents(new Label("Список врачей"), buttons, getGrid());
    }

    @Override
    protected FormLayout getAddEditForm(final boolean edit, final Window window) {

        Binder<Doctor> binder = new Binder();
        FormLayout addEditForm = PersonView.getPersonAddEditForm(edit, binder, getSelectedItem());

        TextField specialization = new TextField("Специализация");
        specialization.setWidth("250");
        specialization.setMaxLength(50);
        binder.forField(specialization)
                .withValidator(new StringLengthValidator("Укажите специализацию", 1, null))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        if (getSelectedItem() != null && edit) {
            specialization.setValue(getSelectedItem().getSpecialization());
        }

        addEditForm.addComponents(specialization, getActions(edit, binder, window, new Doctor()));

        return addEditForm;

    }

    private Grid getDoctorsGrid() throws SQLException {

        Grid<Doctor> doctorsGrid = PersonView.<Doctor>getPersonsGrid();

        doctorsGrid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(200);
        doctorsGrid.setItems(new DoctorController().getAll());
        doctorsGrid.setWidth("1115");

        return doctorsGrid;

    }

    private Button statisticsButton() throws SQLException {

        Grid<DoctorsStatistic> statisticsGrid = new Grid();
        statisticsGrid.addColumn(DoctorsStatistic::getDoctor).setCaption("Врач").setWidth(400);
        statisticsGrid.addColumn(DoctorsStatistic::getNumberOfPrescriptions).setCaption("Количество рецептов").setWidth(200);
        statisticsGrid.setItems(statisticsList());
        statisticsGrid.setFrozenColumnCount(2);
        statisticsGrid.setWidth("615");

        Button statisticsButton = new Button("Показать статистику");
        statisticsButton.setWidth("200");
        statisticsButton.addClickListener(event -> {
            Window statisticsWindow = new Window("Статистика", statisticsGrid);
            statisticsWindow.center();
            statisticsWindow.setResizable(false);
            statisticsWindow.setWidth("615");
            UI.getCurrent().addWindow(statisticsWindow);
        });

        return statisticsButton;
    }

    private List<DoctorsStatistic> statisticsList() throws SQLException {

        List<DoctorsStatistic> statisticsList = new ArrayList<>();

        List<Prescription> prescriptionsList = new PrescriptionController().getAll();

        for (Doctor d: getController().getAll()) {
            int count = 0;
            for (Prescription p: prescriptionsList) {
                if (d.toString().equals(p.getDoctor().toString())) count++;
            }
            statisticsList.add(new DoctorsStatistic(d, count));
        }

        return statisticsList;

    }

    private class DoctorsStatistic {

        private final Doctor doctor;
        private final int numberOfPrescriptions;

        DoctorsStatistic(final Doctor doctor, final int number) {
            this.doctor = doctor;
            this.numberOfPrescriptions = number;
        }

        Doctor getDoctor() {
            return doctor;
        }

        int getNumberOfPrescriptions() {
            return numberOfPrescriptions;
        }

    }

}