package lk.ijse.repository;

public class PlaceOrderRepo {
//    public static boolean placeOrder(PlaceOrderTm po) throws SQLException {
//        Connection connection = DbConnection.getInstance().getConnection();
//        connection.setAutoCommit(false);
//
//        try {
//            boolean isOrderSaved = OrdersRepo.save(po.getOrder());
//            if (isOrderSaved) {
//                boolean isQtyUpdated = ProductRepo.update(po.getOdList());
//                if (isQtyUpdated) {
//                    boolean isOrderDetailSaved = OrderProductDetailsRepo.save(po.getOdList());
//                    if (isOrderDetailSaved) {
//                        connection.commit();
//                        return true;
//                    }
//                }
//            }
//            connection.rollback();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }
//    }


}
