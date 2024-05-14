package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductDetails {
    private  String OrderDetailId;
    private String OrderId;
    private String ProductId;
    private Integer Quantity;
    private Double Weight;
    private Double UnitPrice;

}
