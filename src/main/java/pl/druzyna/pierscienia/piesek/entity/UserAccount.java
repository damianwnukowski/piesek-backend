package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.UUID;

@Entity
@Data
public class UserAccount {

    public UserAccount() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NaturalId
    @Email
    private String email;

    @Length(min = 5)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Length(min = 2)
    private String name;

    @Column(nullable = false)
    @Length(min = 2)
    private String lastName;

    private Role role;

    @JsonIgnore
    private UUID activationToken;

    @Transient
    public boolean isEnabled() {
        return activationToken == null;
    }
}
