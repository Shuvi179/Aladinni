package com.monesoft.refelin.security;

import com.monesoft.refelin.security.filter.JWTAuthorizationFilter;
import com.monesoft.refelin.security.handler.OAuth2AuthenticationFailureHandler;
import com.monesoft.refelin.security.handler.OAuth2AuthenticationSuccessHandler;
import com.monesoft.refelin.security.handler.RestAuthenticationEntryPoint;
import com.monesoft.refelin.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.monesoft.refelin.service.impl.CustomOAuth2UserService;
import com.monesoft.refelin.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/users/login", "/users/register").permitAll()
                    .antMatchers("/css/*", "/js/*").permitAll()
                    .antMatchers("/endpoint-url/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/companies/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/custom/employee").permitAll()
                    .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**").permitAll()
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
