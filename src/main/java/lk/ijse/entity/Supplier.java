package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Supplier {
    private String supplier_id;
    private String name;
    private String contact;
    private String address;
}
