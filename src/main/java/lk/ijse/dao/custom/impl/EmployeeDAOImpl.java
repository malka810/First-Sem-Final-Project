package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("employee_id"), rst.getString("e_name"),rst.getString("department"), rst.getString("role"));
            allEmployee.add(employee);
        }
        return allEmployee;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee WHERE employee_id=?", id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Employee WHERE employee_id=?", id);
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Employee (employee_id,e_name, department,role) VALUES (?,?,?,?)", entity.getEmployee_id(), entity.getE_name(), entity.getDepartment(), entity.getRole());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET e_name=?, department=?, role=? WHERE employee_id=?", entity.getE_name(), entity.getDepartment(), entity.getRole(), entity.getEmployee_id());
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("employee_id");
            int newCustomerId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newCustomerId);
        } else {
            return "E00-001";
        }
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
}
