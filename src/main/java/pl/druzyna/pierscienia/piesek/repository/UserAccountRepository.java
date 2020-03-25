package pl.druzyna.pierscienia.piesek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.druzyna.pierscienia.piesek.entity.UserAccount;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);
    UserAccount findByActivationToken(UUID uuid);
}
