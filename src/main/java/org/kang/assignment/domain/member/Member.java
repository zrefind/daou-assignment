package org.kang.assignment.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kang.assignment.util.converter.RoleSetConverter;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = RoleSetConverter.class)
    @Column(name = "ROLES")
    private RoleSet roles;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Member(Role role, String email, String password) {
        this.roles = new RoleSet();
        this.roles.getRoles().add(role);
        this.email = email;
        this.password = password;
    }

    public static Member of(Role role, String email, String password) {
        return new Member(role, email, password);
    }

}
