package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.dao.PrescriptionController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.*;

public class DoctorsView extends BaseView<Doctor> {

    public DoctorsView() throws SQLException {
        setController(new DoctorController());
        setGrid(getDoctorsGrid());
        SingleSelect<Doctor> selection = getGrid().asSingleSelect();
        setSelection(selection);
        setAddWindow("Добавить врача");
        setEditWindow("Редактирование врача");
        HorizontalLayout buttons = getButtons("400");
        buttons.addComponent(statisticButton());
        VerticalLayout buttonsAndGrid = getButtonsAndGrid();
        buttonsAndGrid.addComponents(new Label("Список врачей"), buttons, getGrid());
    }

    @Override
    protected FormLayout getAddEditForm(final boolean edit, final Window window) {

        Binder<Doctor> binder = new Binder<>();
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

    private Grid<Doctor> getDoctorsGrid() throws SQLException {

        Grid<Doctor> doctorsGrid = PersonView.getPersonsGrid();

        doctorsGrid.addColumn(Doctor::getSpecialization).setCaption("Специализация").setWidth(200);
        doctorsGrid.setItems(new DoctorController().getAll());
        doctorsGrid.setWidth("1115");

        return doctorsGrid;

    }

    private Button statisticButton() throws SQLException {

        Grid<Map.Entry<Doctor, Integer>> statisticGrid = new Grid<>();
        Set<Map.Entry<Doctor, Integer>> it = statisticMap().entrySet();

        statisticGrid.addColumn(Map.Entry::getKey).setCaption("Врач").setWidth(400);
        statisticGrid.addColumn(Map.Entry::getValue).setCaption("Количество рецептов").setWidth(200);
        statisticGrid.setItems(it);
        statisticGrid.setWidth("615");

        Button statisticButton = new Button("Показать статистику");
        statisticButton.setWidth("200");
        statisticButton.addClickListener(event -> {
            Window statisticsWindow = new Window("Статистика", statisticGrid);
            statisticsWindow.center();
            statisticsWindow.setResizable(false);
            statisticsWindow.setWidth("615");
            UI.getCurrent().addWindow(statisticsWindow);
        });

        return statisticButton;

    }

    private Map<Doctor, Integer> statisticMap() throws SQLException {

        List<Prescription> prescriptionsList = new PrescriptionController().getAll();

        Map<Doctor, Integer> stat = new HashMap<>();

        for (Prescription p: prescriptionsList) {
            int count = stat.get(p.getDoctor()) != null ? stat.get(p.getDoctor()) + 1 : 1;
            stat.put(p.getDoctor(), count);
        }

        return stat;

    }

}