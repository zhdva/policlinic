package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.dao.PrescriptionDAO;
import com.haulmont.testtask.dao.PriorityDAO;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.Priority;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

public class PrescriptionsView extends BaseView<Prescription> {

    public PrescriptionsView() {
        super(new PrescriptionDAO());
        setGrid(getPrescriptionsGrid());
        SingleSelect<Prescription> selection = getGrid().asSingleSelect();
        setSelection(selection);
        HorizontalLayout buttons = getButtons("Добавить рецепт", "Редактирование рецепта", "500");
        VerticalLayout view = new VerticalLayout();
        view.addComponents(new Label("Список рецептов"), buttons, filter(), getGrid());
        setView(view);
    }

    @Override
    protected FormLayout getAddEditForm(final boolean edit, final Window window) {

        FormLayout addEditForm = new FormLayout();
        Binder<Prescription> binder = new Binder<>();

        TextArea description = new TextArea("Описание");
        description.setMaxLength(500);
        description.setWidth("350");
        binder.forField(description)
                .withValidator(new StringLengthValidator("Укажите описание", 1, null))
                .bind(Prescription::getDescription, Prescription::setDescription);

        ComboBox<Patient> patient = new ComboBox<>("Пациент");
        patient.setItems(new PatientDAO().getAll());
        patient.setWidth("350");
        binder.forField(patient)
                .asRequired("Укажите пациента")
                .bind(Prescription::getPatient, Prescription::setPatient);

        ComboBox<Doctor> doctor = new ComboBox<>("Врач");
        doctor.setItems(new DoctorDAO().getAll());
        doctor.setWidth("350");
        binder.forField(doctor)
                .asRequired("Укажите врача")
                .bind(Prescription::getDoctor, Prescription::setDoctor);

        DateField created = new DateField("Дата создания");
        created.setWidth("350");
        binder.forField(created)
                .asRequired("Укажите дату создания")
                .bind(Prescription::getCreatedDate, Prescription::setCreatedDate);

        DateField validity = new DateField("Срок действия");
        validity.setWidth("350");
        binder.forField(validity)
                .asRequired("Укажите срок действия")
                .bind(Prescription::getValidity, Prescription::setValidity);

        ComboBox<Priority> priority = new ComboBox<>("Приоритет");
        priority.setWidth("350");
        priority.setItems(new PriorityDAO().getAll());
        binder.forField(priority)
                .asRequired("Укажите приоритет")
                .bind(Prescription::getPriority, Prescription::setPriority);

        if (getSelectedItem() != null && edit) {
            Prescription selectedItem = getSelectedItem();
            description.setValue(selectedItem.getDescription());
            patient.setValue(selectedItem.getPatient());
            doctor.setValue(selectedItem.getDoctor());
            created.setValue(selectedItem.getCreatedDate());
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

    private Grid<Prescription> getPrescriptionsGrid() {

        Grid<Prescription> prescriptionsGrid = new Grid<>();

        prescriptionsGrid.addColumn(Prescription::getDescription).setCaption("Описание").setWidth(400);
        prescriptionsGrid.addColumn(Prescription::getPatient).setCaption("Пациент").setWidth(200);
        prescriptionsGrid.addColumn(Prescription::getDoctor).setCaption("Врач").setWidth(200);
        prescriptionsGrid.addColumn(Prescription::getCreatedDate).setCaption("Дата создания").setWidth(150);
        prescriptionsGrid.addColumn(Prescription::getValidity).setCaption("Срок действия").setWidth(150);
        prescriptionsGrid.addColumn(Prescription::getPriority).setCaption("Приоритет").setWidth(150);
        prescriptionsGrid.setItems(getDao().getAll());
        prescriptionsGrid.setWidth("1265");

        return prescriptionsGrid;
    }

    private HorizontalLayout filter() {

        HorizontalLayout filter = new HorizontalLayout();

        TextField descriptionFilter = new TextField();
        descriptionFilter.setWidth("400");
        descriptionFilter.setPlaceholder("Описание");

        ComboBox<Patient> patientFilter = new ComboBox<>();
        patientFilter.setWidth("300");
        patientFilter.setItems(new PatientDAO().getAll());
        patientFilter.setPlaceholder("Пациент");
        patientFilter.setEmptySelectionAllowed(true);

        ComboBox<Priority> priorityFilter = new ComboBox<>();
        priorityFilter.setWidth("200");
        priorityFilter.setItems(new PriorityDAO().getAll());
        priorityFilter.setPlaceholder("Приоритет");

        Button apply = new Button("Применить");
        apply.setWidth("150");
        apply.addClickListener(event -> {

            String description = descriptionFilter.getValue();

            Patient patient = patientFilter.getValue();
            Long patientId = null;
            if (patient != null) patientId = patient.getId();

            Priority priority = priorityFilter.getValue();
            Long priorityId = null;
            if (priority != null) priorityId = priority.getId();

            getGrid().setItems(new PrescriptionDAO().getFilter(description, patientId, priorityId));

        });

        filter.addComponents(descriptionFilter, patientFilter, priorityFilter, apply);

        return filter;

    }

}

