package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.PatientController;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class PatientsGrid extends BaseGrid<Patient> {

    public PatientsGrid() throws SQLException {
        controller = new PatientController();
        configGrid();
        setGrid();
        getButtons("Добавить пациента", "Редактирование пациента", "400");
        buttonsAndGrid.addComponents(new Label("Список пациентов"), buttons, grid);
    }

    @Override
    protected FormLayout getForm(final boolean edit, final Window window) {

        FormLayout form = new FormLayout();
        Binder<Patient> binder = new Binder<>();

        TextField surname = new TextField("Фамилия");
        surname.setWidth("250");
        surname.setMaxLength(50);
        binder.forField(surname)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(Patient::getSurname, Patient::setSurname);

        TextField name = new TextField("Имя");
        name.setWidth("250");
        name.setMaxLength(50);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(Patient::getName, Patient::setName);

        TextField patronymic = new TextField("Отчество");
        patronymic.setWidth("250");
        patronymic.setMaxLength(50);
        binder.forField(patronymic)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(Patient::getPatronymic, Patient::setPatronymic);

        TextField phone = new TextField("Телефон");
        phone.setWidth("250");
        phone.setMaxLength(12);
        binder.forField(phone)
                .withValidator(new StringLengthValidator("Укажите телефон", 1, null))
                .withValidator(new RegexpValidator("Неверный формат", "[+]{1}[7]{1}[0-9]{10}"))
                .bind(Patient::getPhone, Patient::setPhone);

        if (selectedItem != null && edit) {
            surname.setValue(selectedItem.getSurname());
            name.setValue(selectedItem.getName());
            patronymic.setValue(selectedItem.getPatronymic());
            phone.setValue(selectedItem.getPhone());
        } else {
            phone.setValue("+7");
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
                    Patient newPatient = new Patient();
                    if (binder.writeBeanIfValid(newPatient)) {
                        controller.add(newPatient);
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
        form.addComponent(phone);
        form.addComponent(actions);
        form.setMargin(true);

        return form;

    }

    private void setGrid() throws SQLException {
        grid.addColumn(Patient::getSurname).setCaption("Фамилия").setWidth(300);
        grid.addColumn(Patient::getName).setCaption("Имя").setWidth(300);
        grid.addColumn(Patient::getPatronymic).setCaption("Отчество").setWidth(300);
        grid.addColumn(Patient::getPhone).setCaption("Телефон").setWidth(200);
        grid.setItems(controller.getAll());
        grid.setFrozenColumnCount(4);
        grid.setWidth("1115");
    }

}