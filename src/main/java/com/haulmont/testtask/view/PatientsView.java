package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.PatientController;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class PatientsView extends BaseView<Patient> {

    public PatientsView() throws SQLException {
        setController(new PatientController());
        setGrid(getPatientsGrid());
        SingleSelect<Patient> selection = getGrid().asSingleSelect();
        setSelection(selection);
        setAddWindow("Добавить пациента");
        setEditWindow("Редактирование пациента");
        HorizontalLayout buttons = getButtons("400");
        VerticalLayout buttonsAndGrid = getButtonsAndGrid();
        buttonsAndGrid.addComponents(new Label("Список пациентов"), buttons, getGrid());
    }

    @Override
    protected FormLayout getAddEditForm(final boolean edit, final Window window) {

        Binder<Patient> binder = new Binder<>();
        FormLayout addEditForm = PersonView.getPersonAddEditForm(edit, binder, getSelectedItem());

        TextField phone = new TextField("Телефон");
        phone.setWidth("250");
        phone.setMaxLength(12);
        binder.forField(phone)
                .withValidator(new StringLengthValidator("Укажите телефон", 1, null))
                .withValidator(new RegexpValidator("Неверный формат", "[+]{1}[7]{1}[0-9]{10}"))
                .bind(Patient::getPhone, Patient::setPhone);

        if (getSelectedItem() != null && edit) {
            phone.setValue(getSelectedItem().getPhone());
        } else {
            phone.setValue("+7");
        }

        addEditForm.addComponents(phone, getActions(edit, binder, window, new Patient()));

        return addEditForm;

    }

    private Grid<Patient> getPatientsGrid() throws SQLException {

        Grid<Patient> patientsGrid = PersonView.<Patient>getPersonsGrid();

        patientsGrid.addColumn(Patient::getPhone).setCaption("Телефон").setWidth(200);
        patientsGrid.setItems(getController().getAll());
        patientsGrid.setWidth("1115");

        return patientsGrid;

    }

}