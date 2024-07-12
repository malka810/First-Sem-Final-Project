package lk.ijse.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    private  String user_id;
    private String username;
    private String password;
}
