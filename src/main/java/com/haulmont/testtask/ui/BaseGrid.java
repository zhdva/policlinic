package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.IController;
import com.haulmont.testtask.dao.PatientController;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class BaseGrid<T> {

    protected IController<T> controller;

    protected T selectedItem;

    protected VerticalLayout buttonsAndGrid = new VerticalLayout();

    protected HorizontalLayout buttons = new HorizontalLayout();

    protected Grid<T> grid = new Grid<>();

    protected FormLayout getForm(final boolean edit, final Window window) {
        return null;
    }

    public VerticalLayout getButtonsAndGrid() {
        return buttonsAndGrid;
    }

    protected void configGrid() {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addItemClickListener(event -> {
            if (event.getItem().equals(selectedItem)) {
                selectedItem = null;
            } else {
                selectedItem = event.getItem();
            }
        });
    }

    protected Button removeButton(final IController controller) {
        Button removeButton = new Button("Удалить", clickEvent -> {
            if (selectedItem != null) {
                this.controller = controller;
                try {
                    controller.remove(selectedItem);
                    grid.setItems(controller.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        return removeButton;
    }

    protected Window getWindow(final String caption) {
        Window window = new Window(caption);
        window.center();
        window.setClosable(false);
        window.setDraggable(false);
        window.setResizable(false);
        window.setModal(true);
        return window;
    }

    protected void getButtons(final String addWindowCaption,
                           final String editWindowCaption,
                           final String windowWidth) {

        Button add = new Button("Добавить");
        add.addClickListener(event -> {
            Window addWindow = getWindow(addWindowCaption);
            addWindow.setWidth(windowWidth);
            FormLayout addForm = getForm(false, addWindow);
            addWindow.setContent(addForm);
            UI.getCurrent().addWindow(addWindow);
        });

        Button edit = new Button("Изменить");
        edit.addClickListener(event -> {
            if (selectedItem != null) {
                Window editWindow = getWindow(editWindowCaption);
                editWindow.setWidth(windowWidth);
                FormLayout editForm = getForm(true, editWindow);
                editWindow.setContent(editForm);
                UI.getCurrent().addWindow(editWindow);
            }
        });

        Button remove = removeButton(controller);

        buttons.addComponent(add);
        buttons.addComponent(edit);
        buttons.addComponent(remove);

    }

}