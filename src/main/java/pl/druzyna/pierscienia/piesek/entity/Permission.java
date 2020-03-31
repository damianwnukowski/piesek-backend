package pl.druzyna.pierscienia.piesek.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    ROLE_MANAGE_OWN_ACCOUNT, ROLE_MANAGE_NOTIFICATIONS, ROLE_MANAGE_PET_CATALOG, ROLE_MANAGE_EVENT_CALENDAR,
    ROLE_ADD_USER_ACCOUNT, ROLE_MANAGE_USER_ACCOUNTS;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}