package org.kang.assignment.common;

public class Constants {

    public static final String JWT_ACCESS_DENIED_MESSAGE = "access denied: jwt";
    public static final String JWT_UNAUTHORIZED_MESSAGE = "unauthorized: jwt";

    public static final String ACCESS_TOKEN_NAME = "accessToken";
    public static final String REFRESH_TOKEN_NAME = "refreshToken";
    public static final String JWT_COOKIE_PATH = "/";

    public static String[] IGNORE_URI = {
            "/h2-console/**",
            "/favicon.ico"
    };

    public static final String[] URI_PERMIT_ALL = {
            "/api/auth/sign-up",
            "/api/auth/sign-in",
    };

}
