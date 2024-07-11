package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAllSupplier() throws SQLException,ClassNotFoundException;

    boolean deleteSupplier(String id) throws SQLException,ClassNotFoundException;

    boolean existSupplier(String id) throws SQLException,ClassNotFoundException;

    boolean addSupplier(SupplierDTO supplierDTO) throws SQLException,ClassNotFoundException;

    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException,ClassNotFoundException;

    String generateNewSupplierId() throws SQLException,ClassNotFoundException;
}
