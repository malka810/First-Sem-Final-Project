package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private  String user_id;
    private String username;
    private String password;
}
