package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO Supplier VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplier.getSupplierId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getAddress());
        pstm.setObject(4, supplier.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String SupplierId) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, SupplierId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Sup_Id = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Contact = resultSet.getString(4);

            Supplier supplier = new Supplier(Sup_Id,Name, Address, Contact);

            return supplier;
        }
        return null;
    }
    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET Name = ?, Address = ?, Contact = ? WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplier.getName());
        pstm.setObject(2, supplier.getAddress());
        pstm.setObject(3, supplier.getContact());
        pstm.setObject(4, supplier.getSupplierId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String SupplierId) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, SupplierId);

        return pstm.executeUpdate() > 0;
    }
    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String SupplierId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Contact = resultSet.getString(4);

            Supplier supplier = new Supplier(SupplierId, Name, Address, Contact);
            supList.add(supplier);
        }
        return supList;
    }

}
