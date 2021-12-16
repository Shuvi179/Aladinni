package com.monesoft.refelin.security.filter;

import com.monesoft.refelin.security.jwt.TokenProvider;
import com.monesoft.refelin.service.impl.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final UserService userService;

    public JWTAuthorizationFilter(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        String token = header.replace(TOKEN_PREFIX, "");
        String email = tokenProvider.getUsernameFromToken(token);
        if (StringUtils.hasText(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails user = userService.loadUserByUsername(email);

            if (tokenProvider.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthenticationToken(token, user);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
