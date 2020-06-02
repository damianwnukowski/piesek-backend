package pl.druzyna.pierscienia.piesek.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.druzyna.pierscienia.piesek.dto.user.UserAccountDto;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

@Component
public class UserAccountConverter {

    public UserAccountDto convertEntityToDto(UserAccount userAccount) {
        var userAccountDto = new UserAccountDto();
        BeanUtils.copyProperties(userAccount, userAccountDto);
        return userAccountDto;
    }
}
