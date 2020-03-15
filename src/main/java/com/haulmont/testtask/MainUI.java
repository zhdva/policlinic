package com.haulmont.testtask;

import com.haulmont.testtask.ui.TopPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

        @Override
        protected void init(VaadinRequest vaadinRequest) {

            final VerticalLayout vLayout = new VerticalLayout();
            vLayout.setMargin(true);
            setContent(vLayout);

            TopPanel tp = new TopPanel(vLayout);

        }

    }