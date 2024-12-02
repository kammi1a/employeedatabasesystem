package com.example.employeedatabasesystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {

    // JDBC URL, username, and password
    private static final String URL = "jdbc:postgresql://localhost:5432/employee_db";
    private static final String USER = "postgres"; // Replace with your PostgreSQL username
    private static final String PASSWORD = "123456"; // Replace with your PostgreSQL password

    // Get database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Create a new employee
    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, position, salary, hire_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition());
            statement.setDouble(3, employee.getSalary());
            statement.setDate(4, new java.sql.Date(employee.getHireDate().getTime()));
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("position"),
                            resultSet.getDouble("salary"),
                            resultSet.getDate("hire_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no employee is found
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("salary"),
                        resultSet.getDate("hire_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Update an employee's details
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name = ?, position = ?, salary = ?, hire_date = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition());
            statement.setDouble(3, employee.getSalary());
            statement.setDate(4, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setInt(5, employee.getId());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}