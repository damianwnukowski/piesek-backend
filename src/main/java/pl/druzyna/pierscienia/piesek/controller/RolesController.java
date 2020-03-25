package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.druzyna.pierscienia.piesek.entity.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("role")
public class RolesController {

    @GetMapping("/all")
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/my-permissions")
    public List<String> getMyPermissions() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
