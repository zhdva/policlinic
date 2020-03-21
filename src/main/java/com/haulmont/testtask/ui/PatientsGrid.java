package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.PatientController;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class PatientsGrid extends BaseGrid<Patient> {

    public PatientsGrid() throws SQLException {
        configGrid();
        setGrid();
        getPatientsGrid();
    }

    private void getPatientsGrid() {

        Button addPatient = new Button("Добавить");
        addPatient.addClickListener(event -> {
            Window addWindow = getWindow("Добавить пациента");
            addWindow.setWidth("300");
            FormLayout addForm = getForm(false, addWindow);
            addWindow.setContent(addForm);
            UI.getCurrent().addWindow(addWindow);
            });

        Button editPatient = new Button("Изменить");
        editPatient.addClickListener(event -> {
            if (selectedItem != null) {
                Window editWindow = getWindow("Редактирование пациента");
                editWindow.setWidth("300");
                FormLayout editForm = getForm(true, editWindow);
                editWindow.setContent(editForm);
                UI.getCurrent().addWindow(editWindow);
            }
        });

        Button removePatient = removeButton(new PatientController());

        buttons.addComponent(addPatient);
        buttons.addComponent(editPatient);
        buttons.addComponent(removePatient);

        buttonsAndGrid.addComponent(new Label("Список пациентов"));
        buttonsAndGrid.addComponent(buttons);
        buttonsAndGrid.addComponent(grid);

    }

    private FormLayout getForm(final boolean edit, final Window window) {

        FormLayout form = new FormLayout();
        Binder<Patient> binder = new Binder<>();
        controller = new PatientController();

        TextField surname = new TextField("Фамилия");
        binder.forField(surname)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(Patient::getSurname, Patient::setSurname);

        TextField name = new TextField("Имя");
        binder.forField(name)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(Patient::getName, Patient::setName);

        TextField patronymic = new TextField("Отчество");
        binder.forField(patronymic)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(Patient::getPatronymic, Patient::setPatronymic);

        TextField phone = new TextField("Телефон");
        binder.forField(phone)
                .withValidator(new StringLengthValidator("Укажите телефон", 1, null))
                .bind(Patient::getPhone, Patient::setPhone);

        if (selectedItem != null && edit) {
            surname.setValue(selectedItem.getSurname());
            name.setValue(selectedItem.getName());
            patronymic.setValue(selectedItem.getPatronymic());
            phone.setValue(selectedItem.getPhone());
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
                    Patient newPatient = new Patient();
                    binder.writeBean(newPatient);
                    controller.add(newPatient);
                    if (binder.writeBeanIfValid(newPatient)) {
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
        form.addComponent(phone);
        form.addComponent(actions);
        form.setMargin(true);

        return form;

    }

    private void setGrid() throws SQLException {
        grid.addColumn(Patient::getSurname).setCaption("Фамилия").setWidth(size);
        grid.addColumn(Patient::getName).setCaption("Имя").setWidth(size);
        grid.addColumn(Patient::getPatronymic).setCaption("Отчество").setWidth(size);
        grid.addColumn(Patient::getPhone).setCaption("Телефон").setWidth(size);
        grid.setItems(new PatientController().getAll());
        grid.setWidth("800");
    }

}