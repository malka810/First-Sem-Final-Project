package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }
    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        Customer,Employee,Supplier,Product,Order,Order_Details,QUERY_DAO,User
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case Customer:
                return new CustomerDAOImpl();
            case Employee:
                return new EmployeeDAOImpl();
            case Supplier:
                return new SupplierDAOImpl();
            case Product:
                return new ProductDAOImpl();
            case User:
                return new UserDAOImpl();
            case Order:
                return new OrderDAOImpl();
            case Order_Details:
                return new OrderDetailsDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
