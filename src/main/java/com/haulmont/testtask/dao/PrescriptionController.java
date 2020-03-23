package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController extends JDBCcontroller implements IController<Prescription> {

    @Override
    public void add(Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO prescriptions (description, patient_id, doctor_id, dateCreate, validity, priority)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, prescription.getDescription());
            ps.setLong(2, prescription.getPatient().getId());
            ps.setLong(3, prescription.getDoctor().getId());
            ps.setDate(4, Date.valueOf(prescription.getCreated()));
            ps.setDate(5, Date.valueOf(prescription.getValidity()));
            ps.setString(6, prescription.getPriority());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!ps.isClosed()) {
                ps.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }

    }

    @Override
    public List<Prescription> getAll() throws SQLException {

        IController<Patient> patientController = new PatientController();
        IController<Doctor> doctorController = new DoctorController();

        List<Patient> patients = patientController.getAll();
        List<Doctor> doctors = doctorController.getAll();

        connection = getConnection();
        Statement statement = null;

        String sql = "SELECT id, description, patient_id, doctor_id, created, validity, priority FROM prescriptions";

        List<Prescription> prescriptions = new ArrayList();

        try {

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);



            while (rs.next()) {

                Prescription prescription = new Prescription();
                prescription.setId(rs.getLong("id"));
                prescription.setDescription(rs.getString("description"));

                for (Patient p: patients) {
                    if (p.getId() == rs.getLong("patient_id")) prescription.setPatient(p);
                }

                for (Doctor d: doctors) {
                    if (d.getId() == rs.getLong("doctor_id")) prescription.setDoctor(d);
                }

                prescription.setCreated(rs.getDate("created").toLocalDate());
                prescription.setValidity(rs.getDate("validity").toLocalDate());
                prescription.setPriority(rs.getString("priority"));

                prescriptions.add(prescription);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!statement.isClosed()) {
                statement.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return prescriptions;

    }

    @Override
    public Prescription getById(final Long id) throws SQLException {

        IController<Patient> patientController = new PatientController();
        IController<Doctor> doctorController = new DoctorController();

        List<Patient> patients = patientController.getAll();
        List<Doctor> doctors = doctorController.getAll();

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "SELECT id, description, patient_id, doctor_id, created, validity, priority " +
                "FROM prescriptions WHERE id=?";

        Prescription prescription = new Prescription();

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            prescription.setId(rs.getLong("id"));
            prescription.setDescription(rs.getString("description"));

            for (Patient p: patients) {
                if (p.getId() == rs.getLong("patient_id")) prescription.setPatient(p);
            }

            for (Doctor d: doctors) {
                if (d.getId() == rs.getLong("doctor_id")) prescription.setDoctor(d);
            }

            prescription.setCreated(rs.getDate("created").toLocalDate());
            prescription.setValidity(rs.getDate("validity").toLocalDate());
            prescription.setPriority(rs.getString("priority"));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!ps.isClosed()) {
                ps.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return prescription;

    }

    @Override
    public void update(Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE prescriptions SET desctiption=?, patient_id=?, doctor_id=?, created=?, validity=?, priority=? WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, prescription.getDescription());
            ps.setLong(2, prescription.getPatient().getId());
            ps.setLong(3, prescription.getDoctor().getId());
            ps.setDate(4, Date.valueOf(prescription.getCreated()));
            ps.setDate(5, Date.valueOf(prescription.getValidity()));
            ps.setString(6, prescription.getPriority().toString());
            ps.setLong(7, prescription.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!ps.isClosed()) {
                ps.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }

    }

    @Override
    public void remove(Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM prescriptions WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, prescription.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!ps.isClosed()) {
                ps.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }

        }

    }

}