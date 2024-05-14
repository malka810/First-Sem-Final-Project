package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    private String OrderId;
    private Date OrderDate;
    private Double Payment;
    private String CustomerId;
    private String UserId;

}
