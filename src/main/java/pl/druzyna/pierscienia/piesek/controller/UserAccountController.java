package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.druzyna.pierscienia.piesek.converter.UserAccountConverter;
import pl.druzyna.pierscienia.piesek.dto.user.PasswordChangeDto;
import pl.druzyna.pierscienia.piesek.dto.user.UserAccountDto;
import pl.druzyna.pierscienia.piesek.dto.user.FinishUserCreateDto;
import pl.druzyna.pierscienia.piesek.service.UserAccountService;

import java.util.Optional;

@RestController
@RequestMapping("/user-account")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountConverter userAccountConverter;

    public UserAccountController(UserAccountService userAccountService, UserAccountConverter userAccountConverter) {
        this.userAccountService = userAccountService;
        this.userAccountConverter = userAccountConverter;
    }

    @GetMapping
    public Page<UserAccountDto> searchUsers(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String lastName,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) String role, Pageable pageable) {
        return userAccountService.getUserAccounts(pageable, Optional.ofNullable(name).orElse(""),
                Optional.ofNullable(lastName).orElse(""), Optional.ofNullable(email).orElse(""),
                Optional.ofNullable(role).orElse(""))
                .map(userAccountConverter::convertEntityToDto);
    }

    @GetMapping("/{id}")
    public UserAccountDto findUser(@PathVariable Long id) {
        return userAccountConverter.convertEntityToDto(userAccountService.getUser(id));
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
