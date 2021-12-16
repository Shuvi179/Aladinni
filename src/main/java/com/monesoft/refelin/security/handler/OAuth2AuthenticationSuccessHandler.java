package com.monesoft.refelin.security.handler;

import com.monesoft.refelin.exception.PermissionException;
import com.monesoft.refelin.security.jwt.TokenProvider;
import com.monesoft.refelin.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.monesoft.refelin.service.impl.EmailService;
import com.monesoft.refelin.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.monesoft.refelin.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final EmailService emailService;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = getTargetUrl(request, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String getTargetUrl(HttpServletRequest request, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUri.isPresent() && !emailService.isValidRedirectUrl(redirectUri.get())) {
            throw new PermissionException();
        }
        String validUrl = redirectUri.orElse(getDefaultTargetUrl());
        return defaultUrlToken(validUrl, authentication);
    }

    private String defaultUrlToken(String url, Authentication authentication) {
        String token = tokenProvider.generateToken(authentication);
        return generateUrlWithToken(token, url);
    }

    private String generateUrlWithToken(String token, String url) {
        return UriComponentsBuilder.fromUriString(url)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
