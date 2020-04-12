package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class UserAccount {

    public UserAccount() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    @NotNull
    @NaturalId
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Length(min = 2)
    @NotNull
    private String name;

    @Column(nullable = false)
    @Length(min = 2)
    @NotNull
    private String lastName;

    @NotNull
    private Role role;

    private UUID activationToken;

    @Transient
    public boolean isEnabled() {
        return activationToken == null;
    }
}
