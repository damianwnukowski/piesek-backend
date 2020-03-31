package pl.druzyna.pierscienia.piesek.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Role {
    USER(Permission.ROLE_MANAGE_OWN_ACCOUNT, Permission.ROLE_MANAGE_NOTIFICATIONS, Permission.ROLE_MANAGE_PET_CATALOG,
            Permission.ROLE_MANAGE_EVENT_CALENDAR),
    ADMINISTRATOR(Permission.ROLE_MANAGE_OWN_ACCOUNT, Permission.ROLE_MANAGE_NOTIFICATIONS, Permission.ROLE_MANAGE_PET_CATALOG,
            Permission.ROLE_MANAGE_EVENT_CALENDAR, Permission.ROLE_MANAGE_USER_ACCOUNTS, Permission.ROLE_ADD_USER_ACCOUNT);

    @Getter
    private final List<Permission> permissions;

    Role(Permission... permissions) {
        this.permissions = Arrays.asList(permissions);
    }
}
