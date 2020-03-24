package com.haulmont.testtask;

import com.haulmont.testtask.dao.DoctorController;
import com.haulmont.testtask.dao.IController;
import com.haulmont.testtask.dao.PatientController;
import com.haulmont.testtask.dao.PrescriptionController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.TopPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

        @Override
        protected void init(VaadinRequest vaadinRequest) {

            final VerticalLayout vLayout = new VerticalLayout();
            vLayout.setMargin(true);
            setContent(vLayout);

            try {
                TopPanel tp = new TopPanel(vLayout);
                vLayout.addComponent(tp.getTopButtons());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }