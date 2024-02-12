package org.example.sasha.view;

import org.example.sasha.model.Employee;
import org.example.sasha.repository.EmployeeRepository;
import org.example.sasha.repository.Repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingApp extends JFrame {

    private final Repository<Employee> employeeRepository;
    private final JTable employeeTable;

    public SwingApp() {
        //Window configuration
        setTitle("Employee Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,230);

        //Create a table to display employees
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        //Create buttons for actions
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        //Buttons panel configuration
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //Set buttons styles
        addButton.setBackground(new Color(46,204,113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        updateButton.setBackground(new Color(52,152,219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(231,76,60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        //Create objet Repository to access database
        employeeRepository = new EmployeeRepository();

        //Load initial employees in the table
        refreshEmployeeTable();

        //Add ActionListener to the buttons
        addButton.addActionListener(e -> {
            try {
                addEmployee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        updateButton.addActionListener(e -> updateEmployee());

        deleteButton.addActionListener(e -> deleteEmployee());

    }

    private void refreshEmployeeTable() {
        //Get updated list of employees from the database
        try {
            List<Employee> employees = employeeRepository.findAll();

            //Create a table template and set up the employees data
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Paternal Surname");
            model.addColumn("Maternal Surname");
            model.addColumn("Email");
            model.addColumn("Salary");

            for(Employee employee : employees) {
                Object[] rowData = {
                        employee.getId(),
                        employee.getFirst_name(),
                        employee.getPa_surname(),
                        employee.getMa_surname(),
                        employee.getEmail(),
                        employee.getSalary()
                };
                model.addRow(rowData);
            }

            //Set up updated table template
            employeeTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Error getting employees from the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() throws SQLException {
        //Create form for add employee
        JTextField nameField = new JTextField();
        JTextField pa_surnameField = new JTextField();
        JTextField ma_surnameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] fields = {
                "Name: ", nameField,
                "Paternal Surname: ", pa_surnameField,
                "Maternal Surname: ", ma_surnameField,
                "Email: ", emailField,
                "Salary: ", salaryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add employee", JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            //Create object Employee with entered data
            Employee employee = new Employee();
            employee.setFirst_name(nameField.getText());
            employee.setPa_surname(pa_surnameField.getText());
            employee.setMa_surname(ma_surnameField.getText());
            employee.setEmail(emailField.getText());
            employee.setSalary(Float.parseFloat(salaryField.getText()));

            //Save the new employee in the database
            employeeRepository.save(employee);

            //Update the table with the new employee
            refreshEmployeeTable();

            JOptionPane.showMessageDialog(this, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateEmployee() {
        //Get id of the employee to be updated
        String employeeIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the employee to update", "Update employee", JOptionPane.QUESTION_MESSAGE);

        if(employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                //Get the employee from the database
                Employee employee = employeeRepository.getById(employeeId);

                if(employee != null) {
                    //Create form with employee data
                    JTextField nameField = new JTextField(employee.getFirst_name());
                    JTextField pa_surnameField = new JTextField(employee.getPa_surname());
                    JTextField ma_surnameField = new JTextField(employee.getMa_surname());
                    JTextField emailField = new JTextField(employee.getEmail());
                    JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));

                    Object[] fields = {
                            "Name: ", nameField,
                            "Paternal Surname: ", pa_surnameField,
                            "Maternal Surname: ", ma_surnameField,
                            "Email: ", emailField,
                            "Salary: ", salaryField
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Update employee", JOptionPane.OK_CANCEL_OPTION);
                    if(confirmResult == JOptionPane.OK_OPTION){
                        //Update the employee data with the values entered in the form.
                        employee.setFirst_name(nameField.getText());
                        employee.setPa_surname(pa_surnameField.getText());
                        employee.setMa_surname(ma_surnameField.getText());
                        employee.setEmail(emailField.getText());
                        employee.setSalary(Float.parseFloat(salaryField.getText()));

                        //Save changes to the database
                        employeeRepository.save(employee);

                        //Update data in the interface
                        refreshEmployeeTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No employee with this id was found.","Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Enter a valid numeric value for the ID","Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"Error obtaining employee data from the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEmployee() {
        //Get id of the employee to be deleted
        String employeeIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the employee to delete", "Delete employee", JOptionPane.QUESTION_MESSAGE);
        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                //Confirm delete of employee
                int confirmResult = JOptionPane.showConfirmDialog(this,"Are you sure to remove this employee?", "Confirm delete", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    //Delete employee of database
                    employeeRepository.delete(employeeId);

                    //Update data in the interface
                    refreshEmployeeTable();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Enter a valid numeric value for the ID", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
