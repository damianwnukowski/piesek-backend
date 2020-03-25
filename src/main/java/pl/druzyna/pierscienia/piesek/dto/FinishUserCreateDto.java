package pl.druzyna.pierscienia.piesek.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class FinishUserCreateDto {
    private UUID token;
    @NotNull
    @Length(min = 5, max=64)
    private String password;
    private String passwordConfirm;
}
