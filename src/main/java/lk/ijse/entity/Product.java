package lk.ijse.entity;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private String product_id;
    private  String product_name;
    private String description;
    private String category;
    private int qty_on_hand;
    private BigDecimal weight;
    private BigDecimal unit_price;
}
