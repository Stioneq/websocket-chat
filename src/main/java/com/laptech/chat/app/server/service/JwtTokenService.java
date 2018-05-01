package com.laptech.chat.app.server.service;

import com.laptech.chat.app.server.model.UserEntity;
import com.laptech.chat.app.server.security.JwtSettings;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  @Autowired
  private AuthenticationManager authenticationManager;


  @Autowired
  private JwtSettings jwtSettings;

  public String generateToken(UserEntity entity) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword(),
            new ArrayList<>()));
    return jwtSettings.getTokenPrefix() + " " + generate(entity);
  }

  public UsernamePasswordAuthenticationToken parse(String token) {
    if (token == null) {
      return null;
    }
    String user = Jwts.parser()
        .setSigningKey(jwtSettings.getSecret().getBytes())
        .parseClaimsJws(token.replace(jwtSettings.getTokenPrefix(), ""))
        .getBody()
        .getSubject();
    if (user == null) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }

  private String generate(UserEntity userEntity) {
    return Jwts.builder()
        .setSubject(userEntity.getUsername())
        .setExpiration(
            Date.from(LocalDateTime.now()
                .plus(Long.parseLong(jwtSettings.getExpTime()), ChronoUnit.MILLIS)
                .atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(SignatureAlgorithm.HS512, jwtSettings.getSecret().getBytes())
        .compact();
  }
}
