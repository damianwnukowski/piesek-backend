package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
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
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private Role role;

    private UUID activationToken;

    @Transient
    public boolean isEnabled() {
        return activationToken == null;
    }
}
