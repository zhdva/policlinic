package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.dao.PatientController;
import com.haulmont.testtask.dao.PrescriptionController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionsGrid extends BaseGrid<Prescription> {

    public PrescriptionsGrid() throws SQLException {
        controller = new PrescriptionController();
        configGrid();
        setGrid();
        getButtons("Добавить рецепт", "Редактирование рецепта", "500");
        buttonsAndGrid.addComponents(new Label("Список рецептов"), buttons, filter(), grid);
    }

    @Override
    protected FormLayout getForm(final boolean edit, final Window window) throws SQLException {

        FormLayout form = new FormLayout();
        Binder<Prescription> binder = new Binder<>();

        TextArea description = new TextArea("Описание");
        description.setMaxLength(500);
        description.setWidth("350");
        binder.forField(description)
                .withValidator(new StringLengthValidator("Укажите описание", 1, null))
                .bind(Prescription::getDescription, Prescription::setDescription);

        ComboBox<Patient> patient = new ComboBox("Пациент");
        patient.setItems(new PatientController().getAll());
        patient.setWidth("350");
        binder.forField(patient)
                .asRequired("Укажите пациента")
                .bind(Prescription::getPatient, Prescription::setPatient);

        ComboBox<Doctor> doctor = new ComboBox("Врач");
        doctor.setItems(new DoctorController().getAll());
        doctor.setWidth("350");
        binder.forField(doctor)
                .asRequired("Укажите врача")
                .bind(Prescription::getDoctor, Prescription::setDoctor);

        DateField created = new DateField("Дата создания");
        created.setWidth("350");
        binder.forField(created)
                .asRequired("Укажите дату создания")
                .bind(Prescription::getCreated, Prescription::setCreated);

        DateField validity = new DateField("Срок действия");
        validity.setWidth("350");
        binder.forField(validity)
                .asRequired("Укажите срок действия")
                .bind(Prescription::getValidity, Prescription::setValidity);

        ComboBox<String> priority = new ComboBox("Приоритет");
        priority.setWidth("350");
        List<String> prescriptionPrioritiesList = new Prescription().getListPriorities();
        priority.setItems(prescriptionPrioritiesList);
        binder.forField(priority)
                .asRequired("Укажите приоритет")
                .bind(Prescription::getPriority, Prescription::setPriority);

        if (selectedItem != null && edit) {
            description.setValue(selectedItem.getDescription());
            patient.setValue(selectedItem.getPatient());
            doctor.setValue(selectedItem.getDoctor());
            created.setValue(selectedItem.getCreated());
            validity.setValue(selectedItem.getValidity());
            priority.setValue(selectedItem.getPriority());
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
                    Prescription newPrescription = new Prescription();
                    if (binder.writeBeanIfValid(newPrescription)) {
                        controller.add(newPrescription);
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

        form.addComponent(description);
        form.addComponent(patient);
        form.addComponent(doctor);
        form.addComponent(created);
        form.addComponent(validity);
        form.addComponent(priority);
        form.addComponent(actions);
        form.setMargin(true);

        return form;

    }

    private void setGrid() throws SQLException {
        grid.addColumn(Prescription::getDescription).setCaption("Описание").setWidth(400);
        grid.addColumn(Prescription::getPatient).setCaption("Пациент").setWidth(200);
        grid.addColumn(Prescription::getDoctor).setCaption("Врач").setWidth(200);
        grid.addColumn(Prescription::getCreated).setCaption("Дата создания").setWidth(150);
        grid.addColumn(Prescription::getValidity).setCaption("Срок действия").setWidth(150);
        grid.addColumn(Prescription::getPriority).setCaption("Приоритет").setWidth(150);
        grid.setItems(controller.getAll());
        grid.setWidth("1265");
    }

    private HorizontalLayout filter() throws SQLException {

        HorizontalLayout filter = new HorizontalLayout();

        TextField descriptionFilter = new TextField();
        descriptionFilter.setWidth("400");
        descriptionFilter.setPlaceholder("Описание");

        ComboBox patientFilter = new ComboBox();
        patientFilter.setWidth("300");
        patientFilter.setItems(new PatientController().getAll());
        patientFilter.setPlaceholder("Пациент");
        patientFilter.setEmptySelectionAllowed(true);

        ComboBox priorityFilter = new ComboBox();
        priorityFilter.setWidth("200");
        List<String> prescriptionPrioritiesList = new Prescription().getListPriorities();
        priorityFilter.setItems(prescriptionPrioritiesList);
        priorityFilter.setPlaceholder("Приоритет");

        Button apply = new Button("Применить");
        apply.setWidth("150");
        apply.addClickListener(event -> {
            String description = descriptionFilter.getValue();
            Patient patient = (Patient) patientFilter.getValue();
            Long patientId = null;
            if (patient != null) patientId = patient.getId();
            String priority = (String) priorityFilter.getValue();
            try {
                grid.setItems(new PrescriptionController().getFilter(description, patientId, priority));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(description + patientId + priority);
        });

        filter.addComponents(descriptionFilter, patientFilter, priorityFilter, apply);

        return filter;

    }

}

