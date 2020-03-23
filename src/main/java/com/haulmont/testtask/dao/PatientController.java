package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientController extends JDBCcontroller implements IController<Patient> {

    @Override
    public void add(Patient patient) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO patients (surname, name, patronymic, phone) VALUES (?, ?, ?, ?)";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, patient.getSurname());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getPatronymic());
            ps.setString(4, patient.getPhone());

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
    public List<Patient> getAll() throws SQLException {

        connection = getConnection();
        Statement statement = null;

        String sql = "SELECT id, name, surname, patronymic, phone FROM patients";

        List<Patient> patients = new ArrayList();

        try {

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                Patient patient = new Patient();
                patient.setId(rs.getLong("id"));
                patient.setName(rs.getString("name"));
                patient.setSurname(rs.getString("surname"));
                patient.setPatronymic(rs.getString("patronymic"));
                patient.setPhone(rs.getString("phone"));

                patients.add(patient);

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

        return patients == null ? null : patients;

    }

    @Override
    public Patient getById(final Long id) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "SELECT id, name, surname, patronymic, phone FROM patients WHERE id=?";

        Patient patient = new Patient();

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            patient.setId(rs.getLong("id"));
            patient.setName(rs.getString("name"));
            patient.setSurname(rs.getString("surname"));
            patient.setPatronymic(rs.getString("patronymic"));
            patient.setPhone(rs.getString("phone"));

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

        return patient;

    }

    @Override
    public void update(Patient patient) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE patients SET name=?, surname=?, patronymic=?, phone=? WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getSurname());
            ps.setString(3, patient.getPatronymic());
            ps.setString(4, patient.getPhone());
            ps.setLong(5, patient.getId());

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
    public void remove(Patient patient) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM patients WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, patient.getId());

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