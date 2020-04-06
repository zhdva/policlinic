package com.haulmont.testtask.view;

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

public class PrescriptionsView extends BaseView<Prescription> {

    public PrescriptionsView() throws SQLException {
        setController(new PrescriptionController());
        setGrid(getPrescriptionsGrid());
        SingleSelect<Prescription> selection = getGrid().asSingleSelect();
        setSelection(selection);
        setAddWindow("Добавить рецепт");
        setEditWindow("Редактирование рецепта");
        HorizontalLayout buttons = getButtons("500");
        VerticalLayout buttonsAndGrid = getButtonsAndGrid();
        buttonsAndGrid.addComponents(new Label("Список рецептов"), buttons, filter(), getGrid());
    }

    @Override
    protected FormLayout getAddEditForm(final boolean edit, final Window window) throws SQLException {

        FormLayout addEditForm = new FormLayout();
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

        if (getSelectedItem() != null && edit) {
            Prescription selectedItem = getSelectedItem();
            description.setValue(selectedItem.getDescription());
            patient.setValue(selectedItem.getPatient());
            doctor.setValue(selectedItem.getDoctor());
            created.setValue(selectedItem.getCreated());
            validity.setValue(selectedItem.getValidity());
            priority.setValue(selectedItem.getPriority());
        }

        addEditForm.addComponents(description,
                            patient,
                            doctor,
                            created,
                            validity,
                            priority,
                            getActions(edit, binder, window, new Prescription()));
        addEditForm.setMargin(true);

        return addEditForm;

    }

    private Grid getPrescriptionsGrid() throws SQLException {

        Grid<Prescription> prescriptionsGrid = new Grid<>();

        prescriptionsGrid.addColumn(Prescription::getDescription).setCaption("Описание").setWidth(400);
        prescriptionsGrid.addColumn(Prescription::getPatient).setCaption("Пациент").setWidth(200);
        prescriptionsGrid.addColumn(Prescription::getDoctor).setCaption("Врач").setWidth(200);
        prescriptionsGrid.addColumn(Prescription::getCreated).setCaption("Дата создания").setWidth(150);
        prescriptionsGrid.addColumn(Prescription::getValidity).setCaption("Срок действия").setWidth(150);
        prescriptionsGrid.addColumn(Prescription::getPriority).setCaption("Приоритет").setWidth(150);
        prescriptionsGrid.setItems(getController().getAll());
        prescriptionsGrid.setWidth("1265");

        return prescriptionsGrid;
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
                getGrid().setItems(new PrescriptionController().getFilter(description, patientId, priority));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(description + patientId + priority);
        });

        filter.addComponents(descriptionFilter, patientFilter, priorityFilter, apply);

        return filter;

    }

}

