package com.haulmont.testtask;

import com.haulmont.testtask.view.TopPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

        @Override
        protected void init(VaadinRequest vaadinRequest) {

            VerticalLayout ui = new VerticalLayout();
            ui.setMargin(true);
            setContent(ui);

            TopPanel tp = new TopPanel(ui);
            ui.addComponent(tp.getTopButtons());

        }

    }