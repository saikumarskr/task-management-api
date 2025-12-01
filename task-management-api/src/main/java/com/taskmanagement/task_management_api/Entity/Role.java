package com.taskmanagement.task_management_api.Entity;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.READ,Permission.UPDATE,Permission.WRITE,Permission.DELETE)),
    USER(Set.of(Permission.READ));
    private final Set<Permission> permissionSet;

    Role(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
