package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.entity.CustomEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT Orders.order_id,Orders.order_date,Orders.customer_id,OrderDetails.order_id,OrderDetails.product_id,OrderDetails.quantity,OrderDetails.weight,OrderDetails.unit_price from Orders inner join OrderDetails on Orders.order_id=OrderDetails.order_id where Orders.order_id=?",oid);
        ArrayList<CustomEntity> allRecords= new ArrayList<>();
        while (rst.next()) {
            String oid1 = rst.getString("oid");
            String date = rst.getString("date");
            String customerID = rst.getString("customer_id");
            String productID = rst.getString("product_id");
            int qty = rst.getInt("quantity");
            BigDecimal weight = rst.getBigDecimal("weight");
            BigDecimal unitPrice = rst.getBigDecimal("unit_price");

            CustomEntity customEntity = new CustomEntity(oid1, LocalDate.parse(date), customerID, productID, qty,weight, unitPrice);
            allRecords.add(customEntity);
        }
        return allRecords;
    }
}
