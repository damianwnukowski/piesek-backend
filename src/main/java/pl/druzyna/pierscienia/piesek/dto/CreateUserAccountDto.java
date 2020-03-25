package pl.druzyna.pierscienia.piesek.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import pl.druzyna.pierscienia.piesek.entity.Role;
import pl.druzyna.pierscienia.piesek.entity.UserAccount;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserAccountDto {

    @Email
    @NotNull
    private String email;

    @Length(min = 2)
    @NotNull
    private String name;

    @Length(min = 2)
    @NotNull
    private String lastName;

    @NotNull
    private Role role;

    public UserAccount convertToUserAccountEntity() {
        UserAccount result = new UserAccount();
        BeanUtils.copyProperties(this, result);
        return result;
    }
}
