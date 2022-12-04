package org.kang.assignment.common.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Jwt {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

    public static Jwt of(String grantType, String accessToken, String refreshToken, Long expiresIn) {
        return new Jwt(grantType, accessToken, refreshToken, expiresIn);
    }

}
