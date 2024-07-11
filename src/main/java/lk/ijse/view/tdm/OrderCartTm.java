package lk.ijse.view.tdm;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderCartTm {
    private String product_id;
    private String product_name;
    private BigDecimal weight;
    private int quantity;
    private BigDecimal unit_price;
    private BigDecimal Total;
}
