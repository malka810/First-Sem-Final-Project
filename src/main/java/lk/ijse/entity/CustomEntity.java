package lk.ijse.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomEntity {

    //Customer
    private String customer_id;
    private String name;
    private String contact;
    private String address;


    //Product

    private String product_id;
    private  String product_name;
    private String description;
    private String category;
    private int qty_on_hand;
    private BigDecimal weight;
    private BigDecimal unit_price;

    //Order
    private String order_id;
    private LocalDate order_date;
    private BigDecimal payment;
    //private String customer_id;
    private String user_id;

    //OrderDetails

   // private String order_id;
   // private String product_id;
    private int quantity;

    public CustomEntity(String oid1, LocalDate date, String customerID, String productID, int qty, BigDecimal weight, BigDecimal unitPrice) {
        this.unit_price = unitPrice;
        this.order_id = oid1;
        this.order_date = date;
        this.customer_id = customerID;
        this.product_id = productID;
        this.quantity = qty;
        this.weight = weight;
    }
    // private BigDecimal weight;
   // private BigDecimal unit_price;

}
