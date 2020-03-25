package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.druzyna.pierscienia.piesek.dto.CreateUserAccountDto;
import pl.druzyna.pierscienia.piesek.dto.FinishUserCreateDto;
import pl.druzyna.pierscienia.piesek.service.UserAccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("user-account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/init-user-account-create")
    public void initUserAccountCreate(@RequestBody @Valid CreateUserAccountDto userAccount) {
        userAccountService.initUserAccountCreate(userAccount);
    }

    @PostMapping("/finalize-user-account-create")
    public void finalizeUserAccountCreate(@RequestBody @Valid FinishUserCreateDto userAccount) {
        userAccountService.finishCreateUserAccount(userAccount);
    }
}
