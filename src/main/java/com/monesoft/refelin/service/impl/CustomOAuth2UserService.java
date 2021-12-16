package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.entity.oauth.OAuth2UserInfo;
import com.monesoft.refelin.entity.oauth.OAuth2UserInfoFactory;
import com.monesoft.refelin.entity.oauth.UserOAuth2Principal;
import com.monesoft.refelin.exception.OAuth2AuthenticationProcessingException;
import com.monesoft.refelin.exception.PermissionException;
import com.monesoft.refelin.manager.impl.CompanyManager;
import com.monesoft.refelin.manager.impl.EmployeeRequiredInformationManager;
import com.monesoft.refelin.mapper.UserMapper;
import com.monesoft.refelin.repository.UserRepository;
import com.monesoft.refelin.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.monesoft.refelin.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.USER_REGISTER_AS_HR_COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyManager companyManager;
    private final EmployeeRequiredInformationManager employeeRequiredInformationManager;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findUserByEmail(oAuth2UserInfo.getEmail());
        return userOptional.map(user -> validateUser(user, oAuth2UserRequest))
                .orElseGet(() -> registerUser(oAuth2UserRequest, oAuth2UserInfo));
    }

    private UserOAuth2Principal validateUser(User user, OAuth2UserRequest oAuth2UserRequest) {
        validateProvider(user, oAuth2UserRequest.getClientRegistration().getRegistrationId());
        return new UserOAuth2Principal(user);
    }

    private void validateProvider(User user, String registrationId) {
        if(!registrationId.equals(user.getProvider())) {
            throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                    user.getProvider() + " account. Please use your " + user.getProvider() +
                    " account to login.");
        }
    }

    private UserOAuth2Principal registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User registeredUser = registerUserBasedOnRole(oAuth2UserRequest, oAuth2UserInfo);
        return new UserOAuth2Principal(registeredUser);
    }

    private User registerUserBasedOnRole(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Optional<Cookie> userRole = getUserRegisterRole();
        if (userRole.isEmpty() || !Boolean.parseBoolean(userRole.get().getValue())) {
            return registerBaseCustomUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return registerHRCustomUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    private User registerBaseCustomUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = userMapper.map(oAuth2UserRequest, oAuth2UserInfo, false);
        return userRepository.save(user);
    }

    private User registerHRCustomUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = userMapper.map(oAuth2UserRequest, oAuth2UserInfo, true);
        User userAfterRegister = userRepository.save(user);
        companyManager.addDefaultCompany(userAfterRegister);
        employeeRequiredInformationManager.addDefaultEmployeeRequiredInformation(userAfterRegister);
        return userAfterRegister;
    }

    private Optional<Cookie> getUserRegisterRole() {
        HttpServletRequest request = getCurrentHttpRequest();
        return CookieUtils.getCookie(request, USER_REGISTER_AS_HR_COOKIE_NAME);
    }

    private HttpServletRequest getCurrentHttpRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        throw new PermissionException();
    }
}
