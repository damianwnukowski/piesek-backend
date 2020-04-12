package pl.druzyna.pierscienia.piesek.dto.user;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String password;
    private String passwordConfirm;
}
