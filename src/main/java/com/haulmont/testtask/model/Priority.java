package com.haulmont.testtask.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priority", nullable=false)
    private String priority;

    public Long getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Priority priority1 = (Priority) o;
        return priority.equals(priority1.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority);
    }

    @Override
    public String toString() {
        return priority;
    }
}
