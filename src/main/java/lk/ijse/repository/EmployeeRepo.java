package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql ="INSERT INTO Employee VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getE_Name());
        pstm.setObject(3, employee.getDepartment());
        pstm.setObject(4, employee.getRole());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE EmployeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employeeId);

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET E_Name = ?, Department = ?, Role = ? WHERE EmployeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getE_Name());
        pstm.setObject(2, employee.getDepartment());
        pstm.setObject(3, employee.getRole());
        pstm.setObject(4, employee.getEmployeeId());

        return pstm.executeUpdate() > 0;

    }

    public static Employee searchById(String employeeId) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE EmployeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employeeId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Emp_id = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Department = resultSet.getString(3);
            String Role = resultSet.getString(4);

            Employee employee = new Employee(Emp_id,Name, Department, Role);

            return employee;
        }
        return null;

    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String EmployeeId = resultSet.getString(1);
            String E_Name = resultSet.getString(2);
            String Department = resultSet.getString(3);
            String Role = resultSet.getString(4);

            Employee employee = new Employee(EmployeeId, E_Name, Department, Role);
            empList.add(employee);
        }
        return empList;
    }
}
