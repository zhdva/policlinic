package com.haulmont.testtask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "patients")
public class Patient extends Person {

    @Column(name = "phone", nullable=false)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return getFirstName().equals(patient.getFirstName()) &&
                getLastName().equals(patient.getLastName()) &&
                Objects.equals(getMiddleName(), patient.getMiddleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getMiddleName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLastName());
        sb.append(" ");
        sb.append(getFirstName().charAt(0));
        sb.append(". ");
        if (!getMiddleName().isEmpty()) {
            sb.append(getMiddleName().charAt(0));
            sb.append(". ");
        }
        return sb.toString();
    }

}