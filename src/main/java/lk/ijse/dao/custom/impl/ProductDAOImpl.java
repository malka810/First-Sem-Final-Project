package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Product> allItems = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Product");
        while (rst.next()) {
            allItems.add(new Product(rst.getString("product_id"),rst.getString("product_name"), rst.getString("description"), rst.getString("category"), rst.getInt("qty_on_hand"),rst.getBigDecimal("weight"),rst.getBigDecimal("unit_price")));
        }
        return allItems;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Product WHERE product_id=?", id);
    }

    @Override
    public boolean add(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Product (product_id,product_name, description,category,qty_on_hand,weight,unit_price) VALUES (?,?,?,?,?,?,?)",entity.getProduct_id(),entity.getProduct_name(),entity.getDescription(),entity.getCategory(),entity.getQty_on_hand(),entity.getWeight(),entity.getUnit_price());
    }

    @Override
    public boolean update(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Product SET product_name=?,description=?,category=?,qty_on_hand=?,weight=?, unit_price=? WHERE product_id=?",entity.getProduct_id(),entity.getProduct_name(),entity.getDescription(),entity.getCategory(),entity.getQty_on_hand(),entity.getWeight(),entity.getUnit_price());
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT product_id FROM Product ORDER BY product_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("product_id");
            int newProductId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newProductId);
        } else {
            return "P00-001";
        }
    }

    @Override
    public Product search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
