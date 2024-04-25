package seniorproject.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String dType="User";
    private String username;
    private String email;
    private String password;
    private String[] roles;
}