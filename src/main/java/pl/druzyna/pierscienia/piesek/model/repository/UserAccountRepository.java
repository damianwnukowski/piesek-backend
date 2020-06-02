package pl.druzyna.pierscienia.piesek.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);

    UserAccount findByActivationToken(UUID uuid);

    @Query("SELECT u FROM UserAccount u WHERE " +
            "lower(u.name) LIKE lower(concat('%', :name, '%')) AND " +
            "lower(u.lastName) LIKE lower(concat('%', :lastName, '%')) AND " +
            "lower(u.email) LIKE lower(concat('%', :email, '%')) AND " +
            "lower(u.role) LIKE lower(concat('%', :role, '%'))")
    Page<UserAccount> searchUsers(Pageable pageable, String name, String lastName, String email, String role);
}
