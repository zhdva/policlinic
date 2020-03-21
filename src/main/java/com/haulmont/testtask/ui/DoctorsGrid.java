package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class DoctorsGrid extends BaseGrid<Doctor> {

    public DoctorsGrid() throws SQLException {
        configGrid();
        setGrid();
        getDoctorsGrid();
    }

    private void getDoctorsGrid() {

        Button addDoctor = new Button("Добавить");
        addDoctor.addClickListener(event -> {
            Window addWindow = getWindow("Добавить врача");
            addWindow.setWidth("350");
            FormLayout addForm = getForm(false, addWindow);
            addWindow.setContent(addForm);
            UI.getCurrent().addWindow(addWindow);
        });

        Button editDoctor = new Button("Изменить");
        editDoctor.addClickListener(event -> {
            if (selectedItem != null) {
                Window editWindow = getWindow("Редактирование врача");
                editWindow.setWidth("350");
                FormLayout editForm = getForm(true, editWindow);
                editWindow.setContent(editForm);
                UI.getCurrent().addWindow(editWindow);
            }
        });

        Button removeDoctor = removeButton(new DoctorController());

        buttons.addComponent(addDoctor);
        buttons.addComponent(editDoctor);
        buttons.addComponent(removeDoctor);

        buttonsAndGrid.addComponent(new Label("Список врачей"));
        buttonsAndGrid.addComponent(buttons);
        buttonsAndGrid.addComponent(grid);

    }

    private FormLayout getForm(final boolean edit, final Window window) {

        FormLayout form = new FormLayout();
        Binder<Doctor> binder = new Binder<>();
        controller = new DoctorController();

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
        grid.addColumn(Doctor::getSurname).setCaption("Фамилия").setWidth(size);
        grid.addColumn(Doctor::getName).setCaption("Имя").setWidth(size);
        grid.addColumn(Doctor::getPatronymic).setCaption("Отчество").setWidth(size);
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(size);
        grid.setItems(new DoctorController().getAll());
        grid.setWidth("800");
    }

}