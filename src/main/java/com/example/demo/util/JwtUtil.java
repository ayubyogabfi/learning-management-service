package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.constants.TokenConstants;
import com.example.demo.entity.User;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtil {

  public static Map<String, String> mapTokens(String accessToken, String refreshToken) {
    return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
  }

  public static String createToken(UserDetails userDetails) {
    return createToken(userDetails, DateUtil.nowAfterHoursToDate(TokenConstants.EXPIRES_LIMIT));
  }

  public static String createToken(User user) {
    return createToken(user, DateUtil.nowAfterHoursToDate(TokenConstants.EXPIRES_LIMIT));
  }

  public static String createToken(UserDetails userDetails, Date date) {
    return JWT
      .create()
      .withIssuer(TokenConstants.ISSUER)
      .withClaim(
        TokenConstants.ROLES_CLAIM_KEY,
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
      )
      .withSubject(userDetails.getUsername())
      .withExpiresAt(date)
      .sign(getAlgorithm());
  }

  public static String createToken(User user, Date date) {
    return JWT
      .create()
      .withIssuer(TokenConstants.ISSUER)
      .withSubject(user.getUsername())
      .withExpiresAt(date)
      .sign(getAlgorithm());
  }

  public static String createRefreshToken(UserDetails userDetails) {
    return createToken(userDetails, DateUtil.nowAfterDaysToDate(TokenConstants.EXPIRES_LIMIT));
  }

  public static Boolean verify(String token, User userDetails) {
    try {
      JWTVerifier verifier = JWT.require(getAlgorithm()).build();
      verifier.verify(token);
      return Boolean.TRUE;
    } catch (JWTVerificationException verifyEx) {
      return Boolean.FALSE;
    }
  }

  public static DecodedJWT decodeToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(TokenConstants.TOKEN_KEY.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    return verifier.verify(token);
  }

  public static String getSubject(String token) {
    return decodeToken(token).getSubject();
  }

  public static Claim getClaim(String token, String key) {
    return decodeToken(token).getClaim(key);
  }

  public static Algorithm getAlgorithm() {
    try {
      return Algorithm.HMAC256(TokenConstants.TOKEN_KEY.getBytes());
    } catch (IllegalArgumentException e) {
      return Algorithm.none();
    }
  }
}
