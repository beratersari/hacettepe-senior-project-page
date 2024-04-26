package seniorproject.util.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seniorproject.models.concretes.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    private String token;

}