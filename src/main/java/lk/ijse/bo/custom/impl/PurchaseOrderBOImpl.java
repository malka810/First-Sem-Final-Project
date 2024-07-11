package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailsDAO;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.OrderDetailsDTO;
import lk.ijse.dto.OrdersDTO;
import lk.ijse.dto.ProductDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.OrderDetails;
import lk.ijse.entity.Orders;
import lk.ijse.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Customer);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Product);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Order);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Order_Details);

    @Override
    public ProductDTO searchProduct(String id) throws SQLException, ClassNotFoundException {
        Product p = productDAO.search(id);
        return new ProductDTO(p.getProduct_id(),p.getProduct_name(),p.getDescription(),p.getCategory(),p.getQty_on_hand(),p.getWeight(),p.getUnit_price());
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getCustomer_id(),c.getName(),c.getAddress(),c.getContact());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> convertToDto= new ArrayList<>();
        for (Customer c : customerEntityData) {
            convertToDto.add(new CustomerDTO(c.getCustomer_id(),c.getName(),c.getAddress(),c.getContact()));
        }
        return convertToDto;
    }

    @Override
    public ArrayList<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<Product> entityTypeData = productDAO.getAll();
        ArrayList<ProductDTO> dtoTypeData= new ArrayList<>();
        for (Product p : entityTypeData) {
            dtoTypeData.add(new ProductDTO(p.getProduct_id(),p.getProduct_name(),p.getDescription(),p.getCategory(),p.getQty_on_hand(),p.getWeight(),p.getUnit_price()));
        }
        return dtoTypeData;
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public boolean existProduct(String id) throws SQLException, ClassNotFoundException {
        return productDAO.exist(id);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean purchaseOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DbConnection.getDbConnection().getConnection();
            boolean b1 = orderDAO.exist(dto.getOrder_id());
            /*if order id already exist*/
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = orderDAO.add(new Orders(dto.getOrder_id(), dto.getOrder_date(),dto.getPayment(), dto.getCustomer_id(), dto.getUser_id()));
            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailsDTO d : dto.getOrderDetails()) {
                OrderDetails orderDetails = new OrderDetails(d.getOrder_id(),d.getProduct_id(),d.getQuantity(),d.getWeight(),d.getUnit_price());
                boolean b3 = orderDetailsDAO.add(orderDetails);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                ProductDTO product = findProduct(d.getProduct_id());
                product.setQty_on_hand(product.getQty_on_hand() - d.getQuantity());

                //update item
                boolean b = productDAO.update(new Product(product.getProduct_id(),product.getProduct_name(), product.getDescription(), product.getCategory(),product.getQty_on_hand(),product.getWeight(),product.getUnit_price()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ProductDTO findProduct(String productId) throws SQLException,ClassNotFoundException {
        try {
            Product p = productDAO.search(productId);
            return new ProductDTO(p.getProduct_id(),p.getProduct_name(),p.getDescription(),p.getCategory(),p.getQty_on_hand(),p.getWeight(),p.getUnit_price());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Product " + productId, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    }

