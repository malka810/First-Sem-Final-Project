package lk.ijse.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrdersDTO implements Serializable {
    private String order_id;
    private LocalDate order_date;
    private BigDecimal payment;
    private String customer_id;
    private String user_id;
}
