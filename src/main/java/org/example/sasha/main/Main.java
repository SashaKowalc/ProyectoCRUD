package org.example.sasha.main;

import org.example.sasha.model.Employee;
import org.example.sasha.repository.EmployeeRepository;
import org.example.sasha.repository.Repository;
import org.example.sasha.util.DataBaseConnection;
//import org.example.sasha.view.SwingApp;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println("----------Listing all----------");
        Repository<Employee> repository = new EmployeeRepository();
        repository.findAll().forEach(System.out::println);

        System.out.println("----------Listing by ID----------");
        System.out.println(repository.getById(2));

        /*try(Connection myConn = DataBaseConnection.getInstance()) {

            if(myConn.getAutoCommit()) {
                myConn.setAutoCommit(false);
            }

            try {
                Repository<Employee> repository = new EmployeeRepository(myConn);

                System.out.println("----------Inserting a new employee----------");
                Employee employee = new Employee();
                employee.setFirst_name("Pipi");
                employee.setPa_surname("Kowalczuk");
                employee.setMa_surname("Coronel");
                employee.setEmail("pipi@gmail.com");
                employee.setSalary(10000F);
                employee.setCurp("PIPI14891AASSDD");
                repository.save(employee);
                myConn.commit();
            } catch (SQLException e) {
                myConn.rollback();
                throw new RuntimeException(e);
            }
        }*/

        /*SwingApp app = new SwingApp();
        app.setVisible(true);*/

        /*try (Connection myConn = DataBaseConnection.getInstance()){
            Repository<Employee> repository = new EmployeeRepository();
            Method get (all)
            repository.findAll().forEach(System.out::println);

            /*Method get (by id)
            System.out.println(repository.getById(3));*/

            /*Method Update (create)
            System.out.println("----------Listing----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("----------Inserting an employee----------");
            Employee employee = new Employee();
            employee.setFirst_name("Sasha");
            employee.setPa_surname("Kowalczuk");
            employee.setMa_surname("Sowa");
            employee.setEmail("sasha@mail.com");
            employee.setSalary(19000F);
            repository.save(employee);
            System.out.println("----------New employee inserted----------");
            repository.findAll().forEach(System.out::println);*/

            /*Method Update
            System.out.println("----------Listing----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("----------Editing employee with id 7----------");
            Employee employee = repository.getById(7);
            employee.setFirst_name("Aldana");
            employee.setPa_surname("Coronel");
            employee.setMa_surname("Lopez");
            employee.setEmail("Aldana@gmail.com");
            employee.setSalary(32000F);
            repository.save(employee);
            System.out.println("----------Employee edited----------");
            repository.findAll().forEach(System.out::println);*/

            /*Method Delete
            System.out.println("----------Listing----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("----------Deleting employee with id 7----------");
            repository.delete(7);
            System.out.println("----------Employee deleted----------");
            repository.findAll().forEach(System.out::println);
        }*/
    }
}
