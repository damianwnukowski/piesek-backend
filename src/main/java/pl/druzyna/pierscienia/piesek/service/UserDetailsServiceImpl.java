package pl.druzyna.pierscienia.piesek.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.druzyna.pierscienia.piesek.entity.UserAccount;
import pl.druzyna.pierscienia.piesek.repository.UserAccountRepository;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        if (userAccount == null) {
            throw new UsernameNotFoundException("Username with e-mail " + email + "not found");
        }

        return  new User(userAccount.getEmail(), userAccount.getPassword(), Collections.emptyList());
    }
}
