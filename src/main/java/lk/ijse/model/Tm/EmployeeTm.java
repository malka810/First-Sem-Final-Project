package lk.ijse.model.Tm;

import lk.ijse.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm extends Employee {
    private String EmployeeId;
    private String E_Name;
    private String Department;
    private String Role;
}
