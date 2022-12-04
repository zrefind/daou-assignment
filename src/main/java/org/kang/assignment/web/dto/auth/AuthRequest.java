package org.kang.assignment.web.dto.auth;

import lombok.*;
import org.kang.assignment.domain.member.Member;
import org.kang.assignment.domain.member.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AuthRequest {

    private String email;
    private String password;
    private String passwordConfirm;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.of(Role.MEMBER, email, passwordEncoder.encode(password));
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
