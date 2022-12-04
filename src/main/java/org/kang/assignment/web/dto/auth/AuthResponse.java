package org.kang.assignment.web.dto.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kang.assignment.common.enums.ResponseType;
import org.kang.assignment.common.jwt.Jwt;
import org.kang.assignment.domain.member.Member;
import org.kang.assignment.web.dto.Response;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse extends Response {

    private String email;
    private Jwt jwt;

    private AuthResponse(ResponseType responseType, String email) {
        super(responseType);
        this.email = email;
    }

    public static AuthResponse done(String email) {
        return new AuthResponse(ResponseType.DONE, email);
    }

    public static AuthResponse withToken(Member member, Jwt jwt) {
        return new AuthResponse(member.getEmail(), jwt);
    }

}
