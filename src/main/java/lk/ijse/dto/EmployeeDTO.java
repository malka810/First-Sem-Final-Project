package lk.ijse.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO implements Serializable {
    private String employee_id;
    private String e_name;
    private String department;
    private String role;
}
