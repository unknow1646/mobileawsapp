package com.example.mobilewebws.security;

import com.example.mobilewebws.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public WebSecurity(
      UserService userService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        //.antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL).permitAll()
        //.antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL+"/{userId}").permitAll()
        //.antMatchers(HttpMethod.DELETE, SecurityConstants.SIGN_UP_URL+"/{userId}").permitAll()
        //.antMatchers(HttpMethod.PUT, SecurityConstants.SIGN_UP_URL+"/{userId}").permitAll()
        .anyRequest().authenticated().and()
        .addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }

  public AuthenticationFilter getAuthenticationFilter() throws Exception {
    final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
    authenticationFilter.setFilterProcessesUrl("/users/login");
    return authenticationFilter;
  }
}
