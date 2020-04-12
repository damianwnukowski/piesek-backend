package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.druzyna.pierscienia.piesek.dto.user.PasswordChangeDto;
import pl.druzyna.pierscienia.piesek.dto.user.UserAccountDto;
import pl.druzyna.pierscienia.piesek.dto.user.FinishUserCreateDto;
import pl.druzyna.pierscienia.piesek.service.UserAccountService;

@RestController
@RequestMapping("user-account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/me")
    public UserAccountDto getCurrentUserDetails() {
        return userAccountService.getCurrentUserDetails();
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        userAccountService.changePassword(passwordChangeDto);
    }

    @PostMapping("/init-user-account-create")
    public void initUserAccountCreate(@RequestBody UserAccountDto userAccount) {
        userAccountService.initUserAccountCreate(userAccount);
    }

    @PostMapping("/finalize-user-account-create")
    public void finalizeUserAccountCreate(@RequestBody FinishUserCreateDto userAccount) {
        userAccountService.finishCreateUserAccount(userAccount);
    }
}
