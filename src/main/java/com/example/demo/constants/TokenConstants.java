package com.example.demo.constants;

import com.auth0.jwt.algorithms.Algorithm;

public class TokenConstants {
    public static final String ISSUER = "mendoza";

    public static final String TOKEN_KEY = "S3CR3T";

    public static final long EXPIRES_LIMIT = 3L;

    public final static String ROLES_CLAIM_KEY = "roles";

    public static Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(TokenConstants.TOKEN_KEY.getBytes());
        } catch (IllegalArgumentException e) {
            return Algorithm.none();
        }
    }
}
