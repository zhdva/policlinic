package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Person;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class PersonView {

    protected static <T extends Person> FormLayout getPersonAddEditForm(final boolean edit, final Binder<T> binder, final T selectedItem) {

        FormLayout personAddEditForm = new FormLayout();

        TextField surname = new TextField("Фамилия");
        surname.setWidth("250");
        surname.setMaxLength(50);
        binder.forField(surname)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(T::getSurname, T::setSurname);

        TextField name = new TextField("Имя");
        name.setWidth("250");
        name.setMaxLength(50);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(T::getName, T::setName);

        TextField patronymic = new TextField("Отчество");
        patronymic.setWidth("250");
        patronymic.setMaxLength(50);
        binder.forField(patronymic)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(T::getPatronymic, T::setPatronymic);

        if (selectedItem != null && edit) {
            surname.setValue(selectedItem.getSurname());
            name.setValue(selectedItem.getName());
            patronymic.setValue(selectedItem.getPatronymic());
        }

        personAddEditForm.addComponents(surname, name, patronymic);
        personAddEditForm.setMargin(true);

        return personAddEditForm;

    }

    protected static <T extends Person> Grid getPersonsGrid() throws SQLException {

        Grid<T> personsGrid = new Grid<>();

        personsGrid.addColumn(T::getSurname).setCaption("Фамилия").setWidth(300);
        personsGrid.addColumn(T::getName).setCaption("Имя").setWidth(300);
        personsGrid.addColumn(T::getPatronymic).setCaption("Отчество").setWidth(300);

        return personsGrid;

    }

}
