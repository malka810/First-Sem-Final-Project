package lk.ijse.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplierDTO implements Serializable {
    private String supplier_id;
    private String name;
    private String contact;
    private String address;
}
