package lk.ijse.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO implements Serializable {
    private String customer_id;
    private String name;
    private String address;
    private String contact;

}
