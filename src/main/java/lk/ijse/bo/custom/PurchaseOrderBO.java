package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.OrdersDTO;
import lk.ijse.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    ProductDTO searchProduct(String s) throws SQLException,ClassNotFoundException;

    CustomerDTO searchCustomer(String s) throws SQLException,ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException,ClassNotFoundException;

    ArrayList<ProductDTO> getAllProducts() throws SQLException,ClassNotFoundException;

    String generateOrderID() throws SQLException,ClassNotFoundException;

    boolean existProduct(String id) throws SQLException,ClassNotFoundException;

    boolean existCustomer(String id) throws SQLException,ClassNotFoundException;

    boolean purchaseOrder(OrdersDTO orderDTO) throws SQLException,ClassNotFoundException;
}
