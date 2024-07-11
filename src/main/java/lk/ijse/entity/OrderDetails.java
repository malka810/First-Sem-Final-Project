package lk.ijse.entity;

import lombok.*;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {
    private String order_id;
    private String product_id;
    private int quantity;
    private BigDecimal weight;
    private BigDecimal unit_price;
}
