package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Person;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

public class PersonView {

    protected static <T extends Person> FormLayout getPersonAddEditForm(final boolean edit, final Binder<T> binder, final T selectedItem) {

        FormLayout personAddEditForm = new FormLayout();

        TextField lastName = new TextField("Фамилия");
        lastName.setWidth("250");
        lastName.setMaxLength(50);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator("Укажите фамилию", 1, null))
                .bind(T::getLastName, T::setLastName);

        TextField firstName = new TextField("Имя");
        firstName.setWidth("250");
        firstName.setMaxLength(50);
        binder.forField(firstName)
                .withValidator(new StringLengthValidator("Укажите имя", 1, null))
                .bind(T::getFirstName, T::setFirstName);

        TextField middleName = new TextField("Отчество");
        middleName.setWidth("250");
        middleName.setMaxLength(50);
        binder.forField(middleName)
                .withValidator(new StringLengthValidator("Укажите отчество", 1, null))
                .bind(T::getMiddleName, T::setMiddleName);

        if (selectedItem != null && edit) {
            lastName.setValue(selectedItem.getLastName());
            firstName.setValue(selectedItem.getFirstName());
            middleName.setValue(selectedItem.getMiddleName());
        }

        personAddEditForm.addComponents(lastName, firstName, middleName);
        personAddEditForm.setMargin(true);

        return personAddEditForm;

    }

    protected static <T extends Person> Grid<T> getPersonsGrid() {

        Grid<T> personsGrid = new Grid<>();

        personsGrid.addColumn(T::getLastName).setCaption("Фамилия").setWidth(300);
        personsGrid.addColumn(T::getFirstName).setCaption("Имя").setWidth(300);
        personsGrid.addColumn(T::getMiddleName).setCaption("Отчество").setWidth(300);

        return personsGrid;

    }

}
