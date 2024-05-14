package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderProductDetails;
import lk.ijse.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    public static boolean delete(String productId) throws SQLException {
        String sql = "DELETE FROM Product WHERE ProductId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, productId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Product product) throws SQLException {
        String sql = "INSERT INTO Product VALUES(?, ?, ?, ?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, product.getProductId());
        pstm.setObject(2, product.getProductName());
        pstm.setObject(3, product.getDescription());
        pstm.setObject(4, product.getCategory());
        pstm.setObject(5,product.getQuantityOnHand());
        pstm.setObject(6,product.getWeight());
        pstm.setObject(7,product.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(List<OrderProductDetails>odList) throws SQLException {

        for (OrderProductDetails od : odList) {
            boolean isUpdateQty = updateQty(od.getProductId(), od.getQuantity());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;

        /*
        String sql = "UPDATE Product SET ProductName = ?, Description = ?, Category = ? ,  QuantityOnHand = ? ,  Weight = ?, UnitPrice = ? WHERE ProductId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, product.getProductName());
        pstm.setObject(2, product.getDescription());
        pstm.setObject(3, product.getCategory());
        pstm.setObject(4, product.getQuantityOnHand());
        pstm.setObject(5,product.getWeight());
        pstm.setObject(6,product.getUnitPrice());
        pstm.setObject(7,product.getProductId());

        return pstm.executeUpdate() > 0;*/

    }

    private static boolean updateQty(String productId, Integer quantity) throws SQLException {
        String sql = "UPDATE Product SET QuantityOnHand = QuantityOnHand - ? WHERE ProductId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setInt(1,quantity);
        pstm.setString(2,productId);
        return pstm.executeUpdate() > 0;

    }

    public static Product searchById(String productId) throws SQLException{
        String sql = "SELECT * FROM Product WHERE ProductId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, productId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String ProductId = resultSet.getString(1);
            String ProductName = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String Category = resultSet.getString(4);
            Integer QtyOnHand = resultSet.getInt(5);
            Double Weight = resultSet.getDouble(6);
            Double UnitPrice = resultSet.getDouble(7);


            Product product = new Product(ProductId,ProductName, Description, Category,QtyOnHand,Weight,UnitPrice);

            return product;
        }
        return null;
    }

    public static List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM Product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            String ProductId = resultSet.getString(1);
            String ProuctName = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String Category = resultSet.getString(4);
            Integer QtyOnHand = resultSet.getInt(5);
            Double Weight = resultSet.getDouble(6);
            Double UnitPrice = resultSet.getDouble(7);

            Product product = new Product(ProductId, ProuctName, Description, Category, QtyOnHand, Weight, UnitPrice);
            productList.add(product);
        }
        return productList;
    }

    public static List<String> getProductId()  throws SQLException{
        String sql = "SELECT ProductId FROM Product";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String ProductId = resultSet.getString(1);
            idList.add(ProductId);
        }
        return idList;
    }

}


