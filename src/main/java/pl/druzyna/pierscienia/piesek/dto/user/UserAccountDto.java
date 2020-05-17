package pl.druzyna.pierscienia.piesek.dto.user;

import lombok.Data;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

@Data
public class UserAccountDto {
    private String email;
    private String name;
    private String lastName;
    private UserAccount.Role role;
}
