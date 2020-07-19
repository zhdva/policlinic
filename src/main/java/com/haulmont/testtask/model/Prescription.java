package com.haulmont.testtask.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable=false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable=false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable=false)
    private Doctor doctor;

    @Column(name = "created", nullable=false)
    private LocalDate createdDate;

    @Column(name = "validity", nullable=false)
    private LocalDate validity;

    @ManyToOne
    @JoinColumn(name = "priority_id", nullable=false)
    private Priority priority;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(final Doctor doctor) {
        this.doctor = doctor;
    }

    public void setCreatedDate(final LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setValidity(final LocalDate validity) {
        this.validity = validity;
    }

    public void setPriority(final Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return description.equals(that.description) &&
                patient.equals(that.patient) &&
                doctor.equals(that.doctor) &&
                createdDate.equals(that.createdDate) &&
                validity.equals(that.validity) &&
                priority.equals(that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, patient, doctor, createdDate, validity, priority);
    }

}