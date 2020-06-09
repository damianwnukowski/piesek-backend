package pl.druzyna.pierscienia.piesek.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*/
This controller is only here for swagger documentation. Endpoint /login is handled by Spring Security filters and
shouldn't call any methods here.
 */
@RestController
public class LoginController {

    @PostMapping(path = "/login")
    public void login(@RequestBody LoginDto userAccountDto) {
        throw new IllegalStateException("This method shouldn't be executed, endpoint login should be handled by " +
                "Spring Security filters.");
    }

    @Data
    private static class LoginDto {
        private String email;
        private String password;
    }
}
