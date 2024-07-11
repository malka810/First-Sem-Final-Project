package lk.ijse.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm implements Comparable<CustomerTm>{
    private String customer_id;
    private String name;
    private String address;
    private String contact;

    @Override
    public int compareTo(CustomerTm o) {
        return customer_id.compareTo(o.getCustomer_id());
    }
}
