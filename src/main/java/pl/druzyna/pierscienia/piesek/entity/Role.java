package pl.druzyna.pierscienia.piesek.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Role {
    USER(Permission.MANAGE_OWN_ACCOUNT, Permission.MANAGE_NOTIFICATIONS, Permission.MANAGE_PET_CATALOG,
            Permission.MANAGE_EVENT_CALENDAR),
    ADMINISTRATOR(Permission.MANAGE_OWN_ACCOUNT, Permission.MANAGE_NOTIFICATIONS, Permission.MANAGE_PET_CATALOG,
            Permission.MANAGE_EVENT_CALENDAR, Permission.MANAGE_USER_ACCOUNTS, Permission.ADD_USER_ACCOUNT);

    @Getter
    private final List<Permission> permissions;

    Role(Permission... permissions) {
        this.permissions = Arrays.asList(permissions);
    }
}
