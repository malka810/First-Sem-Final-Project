package lk.ijse.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierDTO implements Serializable {
    private String supplier_id;
    private String name;
    private String contact;
    private String address;

}
