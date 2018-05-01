package com.laptech.chat.app.server.security;

import com.laptech.chat.app.server.service.JwtTokenService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtSettings jwtSettings;
  private final JwtTokenService tokenService;


  public TokenAuthorizationFilter(
      AuthenticationManager authenticationManager,
      JwtSettings jwtSettings, JwtTokenService tokenService) {
    super(authenticationManager);
    this.jwtSettings = jwtSettings;
    this.tokenService = tokenService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain) throws IOException, ServletException {
    String accessToken;
    if(req.getRequestURI().contains("/ws/")) {
      logger.info("ws/info");
      accessToken = req.getParameter(jwtSettings.getGetParamKey());
    }else{
      accessToken = req.getHeader(jwtSettings.getAuthorizationHeader());

    }
    if (!Objects.isNull(accessToken) && accessToken
        .startsWith(jwtSettings.getTokenPrefix())) {
      UsernamePasswordAuthenticationToken authentication = tokenService
          .parse(accessToken.replace(jwtSettings.getTokenPrefix(), "").trim());

      SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    chain.doFilter(req, res);

  }

}
