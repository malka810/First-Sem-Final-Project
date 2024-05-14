package lk.ijse.model.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String ProductId;
    private String ProductName;
    private Double Weight;
    private Integer Quantity;
    private Double UnitPrice;
    private Double Total;

    public CartTm(String productId, String description, int qty, int qtyOnHand, double weight, double unitPrice, double total, JFXButton btnRemove) {
    }
}
