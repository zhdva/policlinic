package com.haulmont.testtask.ui;

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

public class DoctorsGrid extends BaseGrid<Doctor> {

    public DoctorsGrid() throws SQLException {
        controller = new DoctorController();
        configGrid();
        setGrid();
        getButtons("Добавить врача", "Редактирование врача", "400");
        buttons.addComponent(statisticsButton());
        buttonsAndGrid.addComponents(new Label("Список врачей"), buttons, grid);
    }

    @Override
    protected FormLayout getForm(final boolean edit, final Window window) {

        FormLayout form = new FormLayout();
        Binder<Doctor> binder = new Binder<>();

        TextField surname = new TextField("Фамилия");
        surname.setWidth("250");
        surname.setMaxLength(50);
        binder.forField(surname)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(Doctor::getSurname, Doctor::setSurname);

        TextField name = new TextField("Имя");
        name.setWidth("250");
        name.setMaxLength(50);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(Doctor::getName, Doctor::setName);

        TextField patronymic = new TextField("Отчество");
        patronymic.setWidth("250");
        patronymic.setMaxLength(50);
        binder.forField(patronymic)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(Doctor::getPatronymic, Doctor::setPatronymic);

        TextField specialization = new TextField("Специализация");
        specialization.setWidth("250");
        specialization.setMaxLength(50);
        binder.forField(specialization)
                .withValidator(new StringLengthValidator("Укажите специализацию", 1, null))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        if (selectedItem != null && edit) {
            surname.setValue(selectedItem.getSurname());
            name.setValue(selectedItem.getName());
            patronymic.setValue(selectedItem.getPatronymic());
            specialization.setValue(selectedItem.getSpecialization());
        }

        Button ok = new Button("OK");
        ok.setWidth("100");
        ok.addClickListener(event -> {
            try {
                if (edit) {
                    if (binder.writeBeanIfValid(selectedItem)) {
                        controller.update(selectedItem);
                        selectedItem = null;
                        window.close();
                    }
                } else {
                    Doctor newDoctor = new Doctor();
                    if (binder.writeBeanIfValid(newDoctor)) {
                        controller.add(newDoctor);
                        selectedItem = null;
                        window.close();
                    }
                }
                grid.setItems(controller.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button cancel = new Button("Отменить");
        cancel.setWidth("100");
        cancel.addClickListener(event -> {
            window.close();
        });

        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponents(ok, cancel);

        form.addComponent(surname);
        form.addComponent(name);
        form.addComponent(patronymic);
        form.addComponent(specialization);
        form.addComponent(actions);
        form.setMargin(true);

        return form;

    }

    private void setGrid() throws SQLException {
        grid.addColumn(Doctor::getSurname).setCaption("Фамилия").setWidth(300);
        grid.addColumn(Doctor::getName).setCaption("Имя").setWidth(300);
        grid.addColumn(Doctor::getPatronymic).setCaption("Отчество").setWidth(300);
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(200);
        grid.setItems(new DoctorController().getAll());
        grid.setFrozenColumnCount(4);
        grid.setWidth("1115");
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

        for (Doctor d: controller.getAll()) {
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