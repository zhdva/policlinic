package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.dao.IController;
import com.haulmont.testtask.dao.PrescriptionController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorsGrid extends BaseGrid<Doctor> {

    public DoctorsGrid() throws SQLException {
        controller = new DoctorController();
        configGrid();
        setGrid();
        getButtons("Добавить врача", "Редактирование врача", "330");
        //addStatisticsButton();
        buttonsAndGrid.addComponents(new Label("Список пациентов"), buttons, grid);
    }

    @Override
    protected FormLayout getForm(final boolean edit, final Window window) {

        FormLayout form = new FormLayout();
        Binder<Doctor> binder = new Binder<>();

        TextField surname = new TextField("Фамилия");
        binder.forField(surname)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(Doctor::getSurname, Doctor::setSurname);

        TextField name = new TextField("Имя");
        binder.forField(name)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(Doctor::getName, Doctor::setName);

        TextField patronymic = new TextField("Отчество");
        binder.forField(patronymic)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(Doctor::getPatronymic, Doctor::setPatronymic);

        TextField specialization = new TextField("Специализация");
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
        ok.addClickListener(event -> {
            try {
                if (edit) {
                    binder.writeBean(selectedItem);
                    controller.update(selectedItem);
                    if (binder.writeBeanIfValid(selectedItem)) {
                        selectedItem = null;
                        window.close();
                    }
                } else {
                    Doctor newDoctor = new Doctor();
                    binder.writeBean(newDoctor);
                    controller.add(newDoctor);
                    if (binder.writeBeanIfValid(newDoctor)) {
                        selectedItem = null;
                        window.close();
                    }
                }
                grid.setItems(controller.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

        Button cancel = new Button("Отменить");
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
        grid.addColumn(Doctor::getSurname).setCaption("Фамилия").setWidth(200);
        grid.addColumn(Doctor::getName).setCaption("Имя").setWidth(200);
        grid.addColumn(Doctor::getPatronymic).setCaption("Отчество").setWidth(200);
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(200);
        grid.setItems(new DoctorController().getAll());
        grid.setWidth("800");
    }

    /*private void addStatisticsButton() {

        Grid statisticsGrid = new Grid();
        grid.addColumn().setCaption("Врач").setWidth(200);
        grid.addColumn().setCaption("Кол. рецептов").setWidth(100);

        Button statisticsButton = new Button("Показать статистику");
        statisticsButton.addClickListener(event -> {
            Window statisticsWindow = new Window("Статистика", statisticsGrid);
            statisticsWindow.center();
            statisticsWindow.setDraggable(false);
            statisticsWindow.setResizable(false);
            //window.setModal(true);
            UI.getCurrent().addWindow(statisticsWindow);
        });
    }

    public List<String> doctorsList() throws SQLException {

        List<Doctor> doctors = controller.getAll();
        List<String> doctorsList = new ArrayList<>();
        Map<String, int> stat = new HashMap<>();

        for (Doctor d: doctors) {

            String surname = d.getSurname();
            String name = d.getName();
            String patronymic = d.getPatronymic();
            String specialization = d.getSpecialization();

            String doctor = surname + " " + name.charAt(0) + ". " + patronymic.charAt(0) + ". (" + specialization + ")";
            doctorsList.add(doctor);

        }


        return doctorsList;

    }*/

}