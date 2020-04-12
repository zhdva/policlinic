package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionController extends JDBCcontroller implements IController<Prescription> {

    @Override
    public void add(final Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO prescriptions (description, patient_id, doctor_id, created, validity, priority)" +
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

        Map<Long, Patient> patientsMap = getMap(new PatientController());
        Map<Long, Doctor> doctorsMap = getMap(new DoctorController());

        connection = getConnection();
        Statement statement = null;

        String sql = "SELECT id, description, patient_id, doctor_id, created, validity, priority FROM prescriptions";

        List<Prescription> prescriptions = new ArrayList<>();

        try {

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                Prescription prescription = new Prescription();
                prescription.setId(rs.getLong("id"));
                prescription.setDescription(rs.getString("description"));
                prescription.setPatient(patientsMap.get(rs.getLong("patient_id")));
                prescription.setDoctor(doctorsMap.get(rs.getLong("doctor_id")));
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
    public void update(final Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE prescriptions SET description=?, patient_id=?, doctor_id=?, created=?, validity=?, priority=? WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, prescription.getDescription());
            ps.setLong(2, prescription.getPatient().getId());
            ps.setLong(3, prescription.getDoctor().getId());
            ps.setDate(4, Date.valueOf(prescription.getCreated()));
            ps.setDate(5, Date.valueOf(prescription.getValidity()));
            ps.setString(6, prescription.getPriority());
            ps.setLong(7, prescription.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public void remove(final Prescription prescription) throws SQLException {

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

    public List<Prescription> getFilter(final String description, final Long patientId, final String prescriptionPriority) throws SQLException {

        IController<Patient> patientController = new PatientController();
        IController<Doctor> doctorController = new DoctorController();

        List<Patient> patients = patientController.getAll();
        List<Doctor> doctors = doctorController.getAll();

        connection = getConnection();
        Statement statement = null;

        String where = " WHERE ";
        if (description != null) {
            where += "description LIKE '%" + description + "%'";
            if (patientId != null || prescriptionPriority != null) {
                where += " AND ";
            }
        }
        if (patientId != null) {
            where += "patient_id = '" + patientId.toString() + "'";
            if (prescriptionPriority != null) {
                where += " AND ";
            }
        }
        if (prescriptionPriority != null) {
            where += "priority = '" + prescriptionPriority + "'";
        }

        String sql = "SELECT id, description, patient_id, doctor_id, created, validity, priority FROM prescriptions" + where;

        List<Prescription> prescriptions = new ArrayList<>();

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

    private <T extends Person> Map<Long, T> getMap(final IController<T> controller) throws SQLException {

        Map<Long, T> map = new HashMap<>();

        for (T t: controller.getAll()) {
            map.put(t.getId(), t);
        }

        return map;

    }

}