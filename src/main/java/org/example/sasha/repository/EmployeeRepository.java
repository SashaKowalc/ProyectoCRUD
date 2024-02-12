package org.example.sasha.repository;

import org.example.sasha.model.Employee;
import org.example.sasha.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getConnection();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        try(Connection myConn = getConnection();
            Statement myStamt = myConn.createStatement();
            ResultSet myRes = myStamt.executeQuery("SELECT * FROM employees")) {
            while (myRes.next()) {
                Employee e = createEmployee(myRes);
                employees.add(e);
            }
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;

        try(Connection myConn = getConnection();
            PreparedStatement myStamt = myConn.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            myStamt.setInt(1, id);
            try(ResultSet myRes = myStamt.executeQuery()) {
                if(myRes.next()) {
                    employee = createEmployee(myRes);
                }
            }
        }
        return employee;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        String sqlSentence;

        if(employee.getId()!= null && employee.getId()>0) {
            sqlSentence = "UPDATE employees SET first_name=?, pa_surname=?, ma_surname=?, email=?, salary=?, curp=? WHERE id=?";
            System.out.println("/----------Actualizando un registro-------------/\n");
        }else {
            sqlSentence = "INSERT INTO employees(first_name, pa_surname, ma_surname, email, salary, curp) VALUES(?,?,?,?,?,?)";
            System.out.println("/----------Insertando un registro-------------/\n");
        }

        try(Connection myConn = getConnection();
            PreparedStatement myStamt = myConn.prepareStatement(sqlSentence)) {
            myStamt.setString(1, employee.getFirst_name());
            myStamt.setString(2, employee.getPa_surname());
            myStamt.setString(3, employee.getMa_surname());
            myStamt.setString(4, employee.getEmail());
            myStamt.setFloat(5, employee.getSalary());
            myStamt.setString(6, employee.getCurp());

            if (employee.getId() != null && employee.getId() > 0) {
                myStamt.setInt(7, employee.getId());
            }

            int rowsAffected = myStamt.executeUpdate();
            System.out.println("Registros insertados: "+ rowsAffected);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection myConn = getConnection();
             PreparedStatement myStamt = myConn.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            myStamt.setInt(1, id);
            int rowsAffected = myStamt.executeUpdate();
            System.out.println(rowsAffected + " registros eliminados");
        }
    }

    private Employee createEmployee(ResultSet myRes) throws SQLException {
        Employee e = new Employee();
        e.setId(myRes.getInt("id"));
        e.setFirst_name(myRes.getString("first_name"));
        e.setPa_surname(myRes.getString("pa_surname"));
        e.setMa_surname(myRes.getString("ma_surname"));
        e.setEmail(myRes.getString("email"));
        e.setSalary(myRes.getFloat("salary"));
        e.setCurp(myRes.getString("curp"));
        return e;
    }
}
