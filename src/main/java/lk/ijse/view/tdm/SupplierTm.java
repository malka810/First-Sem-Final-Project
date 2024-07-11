package lk.ijse.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplierTm implements Comparable<SupplierTm> {
    private String supplier_id;
    private String name;
    private String contact;
    private String address;

    @Override
    public int compareTo(SupplierTm o) {
        return supplier_id.compareTo(o.getSupplier_id());
    }
}
