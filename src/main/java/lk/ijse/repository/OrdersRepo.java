package lk.ijse.repository;

import lk.ijse.model.Tm.Orders;
import lk.ijse.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String OrderId = resultSet.getString(1);
            return OrderId;
        }
        return null;
    }

    public static boolean save(Orders order) throws SQLException {
//        String sql = "INSERT INTO Orders VALUES(?, ?, ?)";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        pstm.setString(1, order.getOrderId());
//        pstm.setString(2, order.getCustomerId());
//        pstm.setDate(3, order.getDate());
//
//        return pstm.executeUpdate() > 0;
//    }
        return false;
    }
}
