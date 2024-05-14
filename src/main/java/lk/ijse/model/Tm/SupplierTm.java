package lk.ijse.model.Tm;

import lk.ijse.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierTm extends Supplier {
    private String SupplierId;
    private String Name;
    private String Contact;
    private String Address;
}
