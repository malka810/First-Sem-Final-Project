package lk.ijse.model.Tm;

import lk.ijse.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class CustomerTm extends Customer {

    private String CustomerId;
    private String Name;
    private String Address;
    private String Contact;
}
