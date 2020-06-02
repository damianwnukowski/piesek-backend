package pl.druzyna.pierscienia.piesek.dto.user;

import lombok.Data;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

import java.util.Date;

@Data
public class UserAccountDto {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private UserAccount.Role role;
    private Date createdDate;
}
