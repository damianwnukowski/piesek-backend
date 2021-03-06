package pl.druzyna.pierscienia.piesek.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.druzyna.pierscienia.piesek.dto.user.PasswordChangeDto;
import pl.druzyna.pierscienia.piesek.dto.user.UpdateUserAccountDto;
import pl.druzyna.pierscienia.piesek.dto.user.UserAccountDto;
import pl.druzyna.pierscienia.piesek.dto.user.FinishUserCreateDto;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;
import pl.druzyna.pierscienia.piesek.model.repository.UserAccountRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.app.url}")
    private String frontendUrl;

    @Value("${frontend.app.finish.create.user.path}")
    private String finishCreatePath;


    public UserAccountService(UserAccountRepository userAccountRepository, JavaMailSender javaMailSender,
                              PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('MANAGE_USER_ACCOUNTS')")
    @Transactional
    public Page<UserAccount> getUserAccounts(Pageable pageable, String name, String lastName, String email,
                                             String role) {
        return userAccountRepository.searchUsers(pageable, name, lastName, email, role);
    }

    @PreAuthorize("hasRole('ADD_USER_ACCOUNT')")
    @Transactional
    public void initUserAccountCreate(UserAccountDto userAccountDto) {

        UserAccount userAccount = new UserAccount();
        BeanUtils.copyProperties(userAccountDto, userAccount);
        userAccount.setActivationToken(UUID.randomUUID());
        userAccountRepository.save(userAccount);

        ResourceBundle emailResourceBundle = ResourceBundle.getBundle("messages/emails");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userAccount.getEmail());
        message.setSubject(emailResourceBundle.getString("createAccount.title"));
        String url = MessageFormat.format("{0}{1}?token={2}", frontendUrl, finishCreatePath,
                userAccount.getActivationToken());
        String emailBody = MessageFormat.format(emailResourceBundle.getString("createAccount.message"), url);
        message.setText(emailBody);
        javaMailSender.send(message);
    }

    @Transactional
    public void finishCreateUserAccount(FinishUserCreateDto finishUserCreateDto) {
        UserAccount userAccount = userAccountRepository.findByActivationToken(finishUserCreateDto.getToken());

        validatePasswordAndSet(userAccount, finishUserCreateDto.getPassword(), finishUserCreateDto.getPasswordConfirm());
        userAccount.setActivationToken(null);

        userAccountRepository.save(userAccount);
    }

    @PreAuthorize("hasRole('ROLE_MANAGE_OWN_ACCOUNT')")
    @Transactional
    public UserAccountDto getCurrentUserDetails() {
        UserAccount userAccount = userAccountRepository
                .findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserAccountDto userAccountDto = new UserAccountDto();
        BeanUtils.copyProperties(userAccount, userAccountDto);
        return userAccountDto;
    }

    @PreAuthorize("hasRole('ROLE_MANAGE_OWN_ACCOUNT')")
    @Transactional
    public void changePassword(PasswordChangeDto passwordChangeDto) {
        UserAccount userAccount = userAccountRepository
                .findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), userAccount.getPassword())) {
            throw new ValidationException("validation.current.password.not.match");
        }

        validatePasswordAndSet(userAccount, passwordChangeDto.getPassword(), passwordChangeDto.getPasswordConfirm());
        userAccountRepository.save(userAccount);
    }

    @PreAuthorize("hasRole('MANAGE_USER_ACCOUNTS')")
    @Transactional
    public UserAccount getUser(Long id) {
        return userAccountRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('MANAGE_USER_ACCOUNTS')")
    @Transactional
    public void deleteUserAccount(Long id) {
        UserAccount userAccount = userAccountRepository.findById(id).orElse(null);
        String currentUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userAccount == null) {
            throw new ValidationException("validation.user.account.not.found");
        }
        if (currentUserEmail.equals(userAccount.getEmail())) {
            throw new ValidationException("validation.user.account.cant.delete");
        }

        userAccountRepository.delete(userAccount);
    }

    @PreAuthorize("hasRole('MANAGE_USER_ACCOUNTS')")
    @Transactional
    public void updateUserAccount(UpdateUserAccountDto updateUserAccountDto) {
        UserAccount userAccount = userAccountRepository.findById(updateUserAccountDto.getId()).orElse(null);
        if (userAccount == null) {
            throw new ValidationException("validation.user.account.not.exists");
        }
        userAccount.setName(updateUserAccountDto.getName());
        userAccount.setLastName(updateUserAccountDto.getLastName());
        userAccount.setRole(updateUserAccountDto.getRole());
        userAccountRepository.save(userAccount);
    }


    private void validatePasswordAndSet(UserAccount userAccount, String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)){
            throw new ValidationException("validation.passwords.not.match");
        }

        if (newPassword.length() < 8) {
            throw new ValidationException("validation.passwords.too.short");
        }

        userAccount.setPassword(passwordEncoder.encode(newPassword));
    }
}
