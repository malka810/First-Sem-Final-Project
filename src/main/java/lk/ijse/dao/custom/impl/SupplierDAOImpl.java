package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("supplier_id"), rst.getString("name"),rst.getString("contact"), rst.getString("address"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM Supplier WHERE supplier_id=?", id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE supplier_id=?", id);
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Supplier (supplier_id,name,contact,address) VALUES (?,?,?,?)", entity.getSupplier_id(), entity.getName(), entity.getContact(),entity.getAddress());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Supplier SET name=?, contact=?,address=? WHERE supplier_id=?", entity.getName(), entity.getContact(),entity.getAddress(), entity.getSupplier_id());
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supplier_id");
            int newSupplierId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newSupplierId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
}
