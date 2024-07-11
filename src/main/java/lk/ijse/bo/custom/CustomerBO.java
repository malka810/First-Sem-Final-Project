package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO  {
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException,ClassNotFoundException;

    boolean existCustomer(String id) throws SQLException,ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException,ClassNotFoundException;

    boolean addCustomer(CustomerDTO customerDTO) throws SQLException,ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException,ClassNotFoundException;

    String generateNewCustomerID() throws SQLException,ClassNotFoundException;
}
