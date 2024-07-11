package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException,ClassNotFoundException;

    boolean existEmployee(String id) throws SQLException,ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException,ClassNotFoundException;

    boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException,ClassNotFoundException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException,ClassNotFoundException;

    String generateNewEmployeeID() throws SQLException,ClassNotFoundException;
}
