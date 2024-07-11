package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
    ArrayList<ProductDTO> getAllProducts() throws SQLException,ClassNotFoundException;

    boolean saveProduct(ProductDTO productDTO) throws SQLException,ClassNotFoundException;

    boolean existProduct(String id) throws SQLException,ClassNotFoundException;

    String generateNewId()throws SQLException,ClassNotFoundException;

    boolean updateProduct(ProductDTO productDTO) throws SQLException,ClassNotFoundException;

    boolean deleteProduct(String id) throws SQLException,ClassNotFoundException;
}
