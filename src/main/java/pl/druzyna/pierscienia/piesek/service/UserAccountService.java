package pl.druzyna.pierscienia.piesek.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pl.druzyna.pierscienia.piesek.entity.UserAccount;
import pl.druzyna.pierscienia.piesek.repository.UserAccountRepository;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender javaMailSender;

    @Value("${frontend.app.url}")
    private String frontendUrl;

    @Value("${frontend.app.finish.create.user.path}")
    private String finishCreatePath;


    public UserAccountService(UserAccountRepository userAccountRepository, JavaMailSender javaMailSender) {
        this.userAccountRepository = userAccountRepository;
        this.javaMailSender = javaMailSender;
    }

    @PreAuthorize("hasRole('ADD_USER_ACCOUNT')")
    @Transactional
    public void initUserAccountCreate(UserAccount userAccount) {
        ResourceBundle emailResourceBundle = ResourceBundle.getBundle("messages/emails");
        userAccount.setActivationToken(UUID.randomUUID());
        userAccountRepository.save(userAccount);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userAccount.getEmail());
        message.setSubject(emailResourceBundle.getString("createAccount.title"));
        String url = MessageFormat.format("{0}{1}?token={2}", frontendUrl, finishCreatePath,
                userAccount.getActivationToken());
        String emailBody = MessageFormat.format(emailResourceBundle.getString("createAccount.message"), url);
        message.setText(emailBody);
        javaMailSender.send(message);
    }
}
