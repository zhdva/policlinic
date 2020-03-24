package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorController extends JDBCcontroller implements IController<Doctor> {

    @Override
    public void add(final Doctor doctor) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO doctors (surname, name, patronymic, specialization) VALUES (?, ?, ?, ?)";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, doctor.getSurname());
            ps.setString(2, doctor.getName());
            ps.setString(3, doctor.getPatronymic());
            ps.setString(4, doctor.getSpecialization());

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
    public List<Doctor> getAll() throws SQLException {

        connection = getConnection();
        Statement statement = null;

        String sql = "SELECT id, name, surname, patronymic, specialization FROM doctors";

        List<Doctor> doctors = new ArrayList();

        try {

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                Doctor doctor = new Doctor();
                doctor.setId(rs.getLong("id"));
                doctor.setName(rs.getString("name"));
                doctor.setSurname(rs.getString("surname"));
                doctor.setPatronymic(rs.getString("patronymic"));
                doctor.setSpecialization(rs.getString("specialization"));

                doctors.add(doctor);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement.isClosed()) {
                statement.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return doctors;

    }

    @Override
    public Doctor getById(final Long id) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "SELECT id, name, surname, patronymic, specialization FROM doctors WHERE id=?";

        Doctor doctor = new Doctor();

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            doctor.setId(rs.getLong("id"));
            doctor.setName(rs.getString("name"));
            doctor.setSurname(rs.getString("surname"));
            doctor.setPatronymic(rs.getString("patronymic"));
            doctor.setSpecialization(rs.getString("specialization"));

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

        return doctor;

    }

    @Override
    public void update(final Doctor doctor) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE doctors SET name=?, surname=?, patronymic=?, specialization=? WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getSurname());
            ps.setString(3, doctor.getPatronymic());
            ps.setString(4, doctor.getSpecialization());
            ps.setLong(5, doctor.getId());

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
    public void remove(final Doctor doctor) throws SQLException {

        connection = getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM doctors WHERE id=?";

        try {

            ps = connection.prepareStatement(sql);
            ps.setLong(1, doctor.getId());

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
