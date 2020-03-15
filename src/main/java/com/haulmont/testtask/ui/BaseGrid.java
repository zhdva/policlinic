package com.haulmont.testtask.ui;

import com.vaadin.ui.Grid;

public class BaseGrid<T> {

    protected int size = 200;

    protected Grid<T> grid = new Grid<>();

    BaseGrid () {
        grid.getHeightByRows();
    }

    public Grid<T> getGrid() {
        return grid;
    }

}