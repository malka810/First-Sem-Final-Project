package lk.ijse.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomDTO {
    //Customer
    private String customer_id;
    private String name;
    private String address;
    private String contact;

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
    // private BigDecimal weight;
    // private BigDecimal unit_price;
}
