package pl.druzyna.pierscienia.piesek.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);
    UserAccount findByActivationToken(UUID uuid);
}
