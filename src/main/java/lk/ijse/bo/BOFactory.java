package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    public BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        Customer,Employee,Supplier,Product,PO
    }
    public SuperBO getBO(BOTypes types){
        switch (types){
            case Customer:
                return new CustomerBOImpl();
            case Employee:
                return new EmployeeBOImpl();
            case Supplier:
                return new SupplierBOImpl();
            case Product:
                return new ProductBOImpl();
            case PO:
                return new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }

}
