package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.IBaseDAO;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class BaseView<T> {

    private final IBaseDAO<T> dao;
    private Grid<T> grid;
    private SingleSelect<T> selection;
    private VerticalLayout view;

    protected BaseView(final IBaseDAO<T> dao) {
        this.dao = dao;
    }

    protected IBaseDAO<T> getDao() {
        return dao;
    }

    protected Grid<T> getGrid() {
        return grid;
    }

    protected void setGrid(final Grid<T> grid) {
        this.grid = grid;
    }

    protected T getSelectedItem() {
        return selection.getValue();
    }

    public void setSelection(SingleSelect<T> selection) {
        this.selection = selection;
    }

    public VerticalLayout getView() {
        return view;
    }

    public void setView(final VerticalLayout view) {
        this.view = view;
    }

    protected FormLayout getAddEditForm(final boolean edit, final Window window) {
        return null;
    }

    protected HorizontalLayout getButtons(final String addWindowCaption, final String editWindowCaption, final String windowWidth) {

        final String buttonSize = "150";

        Button add = new Button("Добавить");
        add.setWidth(buttonSize);
        add.addClickListener(event -> {
            Window addWindow = getWindow(addWindowCaption);
            FormLayout addForm = getAddEditForm(false, addWindow);
            addWindow.setWidth(windowWidth);
            addWindow.setContent(addForm);
            UI.getCurrent().addWindow(addWindow);
        });
        add.setWidth(buttonSize);

        Button edit = new Button("Изменить");
        edit.setWidth(buttonSize);
        edit.addClickListener(event -> {
            if (getSelectedItem() != null) {
                Window editWindow = getWindow(editWindowCaption);
                FormLayout editForm = getAddEditForm(true, editWindow);
                editWindow.setWidth(windowWidth);
                editWindow.setContent(editForm);
                UI.getCurrent().addWindow(editWindow);
            }
        });
        edit.setWidth(buttonSize);

        Button remove = new Button("Удалить", clickEvent -> {
            if (getSelectedItem() != null) {
                dao.delete(getSelectedItem());
                grid.setItems(dao.getAll());
            }
        });
        remove.setWidth(buttonSize);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(add, edit, remove);

        return buttons;

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

    protected HorizontalLayout getActions(final boolean edit, final Binder<T> binder, final Window window, final T t) {

        final String buttonSize = "100";

        Button ok = new Button("OK");
        ok.setWidth(buttonSize);
        ok.addClickListener(event -> {
            if (edit) {
                if (binder.writeBeanIfValid(getSelectedItem())) {
                    dao.update(getSelectedItem());
                    window.close();
                }
            } else {
                if (binder.writeBeanIfValid(t)) {
                    dao.add(t);
                    window.close();
                }
            }
            grid.setItems(dao.getAll());
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