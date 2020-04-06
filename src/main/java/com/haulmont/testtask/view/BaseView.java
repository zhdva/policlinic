package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.IController;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class BaseView<T> {

    private IController<T> controller;
    private SingleSelect<T> selection;
    private VerticalLayout buttonsAndGrid;
    private HorizontalLayout buttons;
    private FormLayout addForm;
    private FormLayout editForm;
    private Grid<T> grid;
    private Window addWindow;
    private Window editWindow;

    protected BaseView() {
        buttonsAndGrid = new VerticalLayout();
        buttons = new HorizontalLayout();
        grid = new Grid<>();
    }

    protected IController<T> getController() {
        return controller;
    }

    protected T getSelectedItem() {
        return selection.getValue();
    }

    public VerticalLayout getButtonsAndGrid() {
        return buttonsAndGrid;
    }

    protected HorizontalLayout getButtons(final String windowWidth) {

        final String buttonSize = "150";

        Button add = new Button("Добавить");
        add.setWidth(buttonSize);
        add.addClickListener(event -> {
            try {
                setAddForm(getAddEditForm(false, getAddWindow()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addWindow.setWidth(windowWidth);
            addWindow.setContent(addForm);
            UI.getCurrent().addWindow(addWindow);
        });

        Button edit = new Button("Изменить");
        edit.setWidth(buttonSize);
        edit.addClickListener(event -> {
            if (getSelectedItem() != null) {
                try {
                    setEditForm(getAddEditForm(true, getEditWindow()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                editWindow.setWidth(windowWidth);
                editWindow.setContent(editForm);
                UI.getCurrent().addWindow(editWindow);
            }
        });

        Button remove = new Button("Удалить", clickEvent -> {
            if (getSelectedItem() != null) {
                try {
                    controller.remove(getSelectedItem());
                    grid.setItems(controller.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        remove.setWidth(buttonSize);

        buttons.addComponents(add, edit, remove);

        return buttons;

    }

    protected Grid<T> getGrid() {
        return grid;
    }

    protected Window getAddWindow() {
        return addWindow;
    }

    protected Window getEditWindow() {
        return editWindow;
    }

    protected FormLayout getAddEditForm(final boolean edit, final Window window) throws SQLException {
        return null;
    }

    protected void setController(final IController<T> controller) {
        this.controller = controller;
    }

    public void setSelection(SingleSelect<T> selection) {
        this.selection = selection;
    }

    protected void setAddForm(final FormLayout addForm) {
        this.addForm = addForm;
    }

    protected void setEditForm(final FormLayout editForm) {
        this.editForm = editForm;
    }

    protected void setGrid(final Grid<T> grid) {
        this.grid = grid;
    }

    protected void setAddWindow(final String addWindowCaption) {
        this.addWindow = getWindow(addWindowCaption);
    }

    protected void setEditWindow(final String editWindowCaption) {
        this.editWindow = getWindow(editWindowCaption);;
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

    protected HorizontalLayout getActions(final boolean edit, final Binder binder, final Window window, final T t) {

        final String buttonSize = "100";

        Button ok = new Button("OK");
        ok.setWidth(buttonSize);
        ok.addClickListener(event -> {
            try {
                if (edit) {
                    if (binder.writeBeanIfValid(getSelectedItem())) {
                        controller.update(getSelectedItem());
                        window.close();
                    }
                } else {
                    if (binder.writeBeanIfValid(t)) {
                        controller.add(t);
                        window.close();
                    }
                }
                grid.setItems(controller.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button cancel = new Button("Отменить");
        cancel.setWidth(buttonSize);
        cancel.addClickListener(event -> {
            window.close();
        });

        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponents(ok, cancel);

        return actions;

    }

}