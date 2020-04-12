package pl.druzyna.pierscienia.piesek.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class FinishUserCreateDto {
    private UUID token;
    private String password;
    private String passwordConfirm;
}
