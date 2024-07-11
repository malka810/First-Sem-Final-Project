package lk.ijse.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    List<OrderDetailsDTO> orderDetails;

    public OrdersDTO(String orderId, LocalDate orderDate, String customerId, List<OrderDetailsDTO> orderDetails) {
        this.order_id = orderId;
        this.order_date = orderDate;
        this.customer_id = customerId;
        this.orderDetails = orderDetails;
    }
}
