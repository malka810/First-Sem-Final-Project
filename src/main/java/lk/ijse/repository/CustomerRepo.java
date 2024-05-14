package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class CustomerRepo {

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String CustomerId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustomerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, CustomerId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Cus_Id = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Contact = resultSet.getString(4);

            Customer customer = new Customer(Cus_Id,Name, Address, Contact);

            return customer;
        }
        return null;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET Name = ?, Address = ?, Contact = ? WHERE CustomerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getContact());
        pstm.setObject(4, customer.getCustomerId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String CustomerId) throws SQLException {
        String sql = "DELETE FROM Customer WHERE CustomerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, CustomerId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Contact = resultSet.getString(4);

            Customer customer = new Customer(CustomerId, Name, Address, Contact);
            cusList.add(customer);
        }
        return cusList;
    }

    public static List<String> getIds() throws SQLException{
        String sql = "SELECT CustomerId FROM Customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            idList.add(CustomerId);
        }
        return idList;
    }
}
