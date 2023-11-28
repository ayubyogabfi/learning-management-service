package com.example.demo.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.example.demo.auth.JwtAuthenticationFilter;
import com.example.demo.auth.JwtAuthorizationFilter;
import com.example.demo.constants.APIConstants;
import com.example.demo.constants.AppConstants;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(
      authenticationManagerBean(),
      userService
    );
    JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter();

    authenticationFilter.setFilterProcessesUrl(APIConstants.LOGIN_PATH);

    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http
      .addFilter(authenticationFilter)
      .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
      .csrf()
      .disable()
      .sessionManagement()
      .sessionCreationPolicy(STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers(
        APIConstants.ROOT_ENTRY_POINT,
        APIConstants.API_DOCS_ENTRY_POINT,
        APIConstants.SWAGGER_UI_ENTRY_POINT,
        APIConstants.SWAGGER_HTML_ENTRY_POINT,
        APIConstants.SWAGGER_RESOURCES_ENTRY_POINT,
        APIConstants.LOGIN_PATH,
        APIConstants.REFRESH_TOKEN_ENTRY_POINT,
        APIConstants.ERROR_ENTRY_POINT,
        APIConstants.SECTION_ENTRY_POINT,
        APIConstants.REGISTER_ACCOUNT_PATH,
        "/v1/article"
      )
      .permitAll()
      .antMatchers(HttpMethod.POST, APIConstants.USERS_ENTRY_POINT)
      .permitAll() // permit register
      .antMatchers(HttpMethod.GET, APIConstants.USERS_ENTRY_POINT)
      .hasAnyAuthority(AppConstants.ROLE_USER)
      .anyRequest()
      .authenticated();
  }
}
