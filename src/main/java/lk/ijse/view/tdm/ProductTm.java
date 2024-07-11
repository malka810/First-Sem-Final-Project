package lk.ijse.view.tdm;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductTm implements Comparable<ProductTm> {
    private String product_id;
    private  String product_name;
    private String description;
    private String category;
    private int qty_on_hand;
    private BigDecimal weight;
    private BigDecimal unit_price;

    @Override
    public int compareTo(ProductTm o) {
        return product_id.compareTo(o.getProduct_id());
    }
}
