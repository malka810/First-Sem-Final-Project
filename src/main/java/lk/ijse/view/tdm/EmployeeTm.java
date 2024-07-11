package lk.ijse.view.tdm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTm implements Comparable<EmployeeTm> {
    private String employee_id;
    private String e_name;
    private String department;
    private String role;

    @Override
    public int compareTo(EmployeeTm o) {
        return employee_id.compareTo(o.getEmployee_id());
    }
}
