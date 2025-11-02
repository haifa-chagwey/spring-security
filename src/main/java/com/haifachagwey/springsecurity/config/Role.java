package com.haifachagwey.springsecurity.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.haifachagwey.springsecurity.config.Permission.*;

public enum Role {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            STUDENT_READ,
            STUDENT_WRITE,
            COURSE_READ,
            COURSE_WRITE
    )),
    ADMIN_TRAINEE(Sets.newHashSet(
            STUDENT_READ,
            COURSE_READ
    ));

    private final Set<Permission> rolePermissions;

    Role(Set<Permission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Set<Permission> getRolePermissions() {
        return rolePermissions;
    }
//    Ex: role: ROLE_ADMIN_TRAINEE
//    its permissions: student:read, course:read

    public Set<GrantedAuthority> getRoleGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = getRolePermissions().stream()
                .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;

    }
}
