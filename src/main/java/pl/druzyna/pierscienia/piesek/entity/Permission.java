package pl.druzyna.pierscienia.piesek.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    MANAGE_OWN_ACCOUNT, MANAGE_NOTIFICATIONS, MANAGE_PET_CATALOG, MANAGE_EVENT_CALENDAR,
    ADD_USER_ACCOUNT, MANAGE_USER_ACCOUNTS;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}