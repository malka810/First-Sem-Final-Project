package lk.ijse.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDTO implements Serializable {
    private String order_id;
    private String product_id;
    private int quantity;
    private BigDecimal weight;
    private BigDecimal unit_price;
}
