package lk.ijse.model.Tm;

import lk.ijse.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductTm extends Product {
    private String ProductId;
    private String ProductName;
    private String Description;
    private String Category;
    private Integer QuantityOnHand;
    private Double Weight;
    private Double UnitPrice;
}
