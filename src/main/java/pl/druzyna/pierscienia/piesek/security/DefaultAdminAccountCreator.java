package pl.druzyna.pierscienia.piesek.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;
import pl.druzyna.pierscienia.piesek.model.repository.UserAccountRepository;

@Component
public class DefaultAdminAccountCreator implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(DefaultAdminAccountCreator.class);

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${defaultAdminAccount.email}")
    private String adminAccountEmail;

    @Value("${defaultAdminAccount.password}")
    private String adminAccountPassword;

    public DefaultAdminAccountCreator(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        UserAccount defaultAdminAccount = new UserAccount();
        log.info("Creating default admin account with email {} and password {}", adminAccountEmail, adminAccountPassword);
        defaultAdminAccount.setEmail(adminAccountEmail);
        defaultAdminAccount.setPassword(passwordEncoder.encode(adminAccountPassword));
        defaultAdminAccount.setRole(UserAccount.Role.ADMINISTRATOR);
        defaultAdminAccount.setActivationToken(null);
        defaultAdminAccount.setName("TestName");
        defaultAdminAccount.setLastName("TestLastName");
        userAccountRepository.save(defaultAdminAccount);
    }
}
