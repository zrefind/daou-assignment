package org.kang.assignment.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleSet {

    private final Set<Role> roles = new HashSet<>();

    public RoleSet(Set<Role> roles) {
        this.roles.addAll(roles);
    }

    public Set<Role> getRolesReadOnly() {
        return Collections.unmodifiableSet(roles);
    }

    protected Set<Role> getRoles() {
        return this.roles;
    }

}
