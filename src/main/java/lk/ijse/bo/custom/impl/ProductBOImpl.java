package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProductBO;
import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.dto.ProductDTO;
import lk.ijse.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO  = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Product);
    @Override
    public ArrayList<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<Product> allEntityData = productDAO.getAll();
        ArrayList<ProductDTO> allDTOData= new ArrayList<>();
        for (Product p : allEntityData) {
            allDTOData.add(new ProductDTO(p.getProduct_id(),p.getProduct_name(),p.getDescription(),p.getCategory(),p.getQty_on_hand(),p.getWeight(),p.getUnit_price()));
        }
        return allDTOData;
    }

    @Override
    public boolean saveProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.add(new Product(dto.getProduct_id(),dto.getProduct_name(),dto.getDescription(),dto.getCategory(),dto.getQty_on_hand(),dto.getWeight(),dto.getUnit_price()));
    }

    @Override
    public boolean existProduct(String id) throws SQLException, ClassNotFoundException {
        return productDAO.exist(id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return productDAO.generateNewID();
    }

    @Override
    public boolean updateProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.add(new Product(dto.getProduct_id(),dto.getProduct_name(),dto.getDescription(),dto.getCategory(),dto.getQty_on_hand(),dto.getWeight(),dto.getUnit_price()));
    }

    @Override
    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException {
        return productDAO.delete(id);
    }
}
