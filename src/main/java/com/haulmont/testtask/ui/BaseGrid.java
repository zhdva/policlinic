package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.IController;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class BaseGrid<T> {

    protected int size = 200;

    protected IController controller;

    protected T selectedItem;

    protected VerticalLayout buttonsAndGrid = new VerticalLayout();

    protected HorizontalLayout buttons = new HorizontalLayout();

    protected Grid<T> grid = new Grid<>();

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

}