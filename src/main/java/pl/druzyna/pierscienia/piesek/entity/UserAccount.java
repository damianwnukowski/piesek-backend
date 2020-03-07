package pl.druzyna.pierscienia.piesek.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class UserAccount {
    private Long id;
    private String email;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(nullable = false, unique = true)
    @Email
    public String getEmail() {
        return email;
    }

    @Column(nullable = false)
    @Length(min = 5)
    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
