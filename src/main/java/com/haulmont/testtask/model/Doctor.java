package com.haulmont.testtask.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {

    @Column(name = "specialization", nullable=false)
    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return specialization.equals(doctor.specialization) &&
                getFirstName().equals(doctor.getFirstName()) &&
                getLastName().equals(doctor.getLastName()) &&
                Objects.equals(getMiddleName(), doctor.getMiddleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization, getFirstName(), getLastName(), getMiddleName());
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
        sb.append("(");
        sb.append(specialization);
        sb.append(")");
        return sb.toString();
    }
}