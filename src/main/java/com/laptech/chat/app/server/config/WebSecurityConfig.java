package com.laptech.chat.app.server.config;

import com.laptech.chat.app.server.security.JwtSettings;
import com.laptech.chat.app.server.security.TokenAuthorizationFilter;
import com.laptech.chat.app.server.service.JwtTokenService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${jwt.sign-up-url}")
  private String signUpUrl;

  @Autowired
  @Qualifier("userDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtSettings jwtSettings;

  @Autowired
  private JwtTokenService tokenService;


  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .antMatchers(signUpUrl, "/user/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new TokenAuthorizationFilter(authenticationManager(), jwtSettings, tokenService))
        .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

  }

  private AuthenticationEntryPoint unauthorizedEntryPoint() {
    return (request, response, authException) -> {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    };
  }


}
