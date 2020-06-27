package br.com.monthalcantara.projetofinal.security;

public class SecurityConstants {
    static final String SECRET = "DevDojoFoda";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/usuarios/login";
    static final long EXPIRATION_TIME = 86400000L;
}
