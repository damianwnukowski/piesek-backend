package pl.druzyna.pierscienia.piesek.dto.user;

import lombok.Data;
import pl.druzyna.pierscienia.piesek.entity.Role;

@Data
public class UserAccountDto {
    private String email;
    private String name;
    private String lastName;
    private Role role;
}
