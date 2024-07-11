package lk.ijse.view.tdm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Orders {
    private String order_id;
    private LocalDate order_date;
    private BigDecimal payment;
    private String customer_id;
    private String user_id;
}
