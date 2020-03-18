package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController extends JDBCcontroller implements IController<Prescription> {

    @Override
    public void add(Prescription prescription) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO patients (description, patient, doctor, dateCreate, validity, priority)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, prescription.getDescription());
            ps.setString(2, prescription.getPatient());
            ps.setString(3, prescription.getDoctor());
            ps.setDate(4, (Date) prescription.getDateCreate());
            ps.setDate(5, (Date) prescription.getValidity());
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

        connection = getConnection();
        Statement statement = null;

        String sql = "SELECT id, description, patient, doctor, dateCreate, validity, priority FROM prescription";

        List<Prescription> prescriptions = new ArrayList();

        try {

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                Prescription prescription = new Prescription();
                prescription.setId(rs.getLong("id"));
                prescription.setDescription(rs.getString("description"));
                prescription.setPatient(rs.getString("patient"));
                prescription.setDoctor(rs.getString("doctor"));
                prescription.setDateCreate(rs.getDate("dateCreate"));
                prescription.setValidity(rs.getDate("validity"));
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
    public Prescription getById(Long id) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "SELECT id, description, patient, doctor, dateCreate, validity, priority " +
                "FROM prescription WHERE id=?";

        Prescription prescription = new Prescription();

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            prescription.setId(rs.getLong("id"));
            prescription.setDescription(rs.getString("description"));
            prescription.setPatient(rs.getString("patient"));
            prescription.setDoctor(rs.getString("doctor"));
            prescription.setDateCreate(rs.getDate("dateCreate"));
            prescription.setValidity(rs.getDate("validity"));
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

        String sql = "UPDATE prescriptions SET desctiption=?, patient=?, doctor=?, dateCreate=?, validity=?, priority=? WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, prescription.getDescription());
            ps.setString(2, prescription.getPatient());
            ps.setString(3, prescription.getDoctor());
            ps.setDate(4, (Date) prescription.getDateCreate());
            ps.setDate(5, (Date) prescription.getValidity());
            ps.setString(6, prescription.getPriority());
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