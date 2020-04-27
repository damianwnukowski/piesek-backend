package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
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
    @Enumerated(EnumType.STRING)
    private Role role;

    private UUID activationToken;

    @Transient
    public boolean isEnabled() {
        return activationToken == null;
    }

    public enum Permission implements GrantedAuthority {
        ROLE_MANAGE_OWN_ACCOUNT, ROLE_MANAGE_NOTIFICATIONS, ROLE_MANAGE_PET_CATALOG, ROLE_MANAGE_EVENT_CALENDAR,
        ROLE_ADD_USER_ACCOUNT, ROLE_MANAGE_USER_ACCOUNTS;

        @Override
        public String getAuthority() {
            return this.toString();
        }
    }

    public enum Role {
        USER(Permission.ROLE_MANAGE_OWN_ACCOUNT, Permission.ROLE_MANAGE_NOTIFICATIONS, Permission.ROLE_MANAGE_PET_CATALOG,
                Permission.ROLE_MANAGE_EVENT_CALENDAR),
        ADMINISTRATOR(Permission.ROLE_MANAGE_OWN_ACCOUNT, Permission.ROLE_MANAGE_NOTIFICATIONS, Permission.ROLE_MANAGE_PET_CATALOG,
                Permission.ROLE_MANAGE_EVENT_CALENDAR, Permission.ROLE_MANAGE_USER_ACCOUNTS, Permission.ROLE_ADD_USER_ACCOUNT);

        @Getter
        private final List<Permission> permissions;

        Role(Permission... permissions) {
            this.permissions = Arrays.asList(permissions);
        }
    }
}
