package org.asi.authservice;


import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.security.SecurityUserDetailsImpl;
import org.asi.authservice.service.UserService;
import org.asi.authservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @BeforeAll
    public static void setup() {
        var securityUserDetails = mock(SecurityUserDetailsImpl.class);
        var authentication = mock(Authentication.class);
        var securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(securityUserDetails);
        given(securityContext.getAuthentication().getPrincipal()).willReturn(securityUserDetails);

        // mocking user id in security context
//        given(user.getId()).willReturn("123e4567-e89b-12d3-a456-426614174000");
    }

    @Test
    public void shouldCreateUser() {

        // given
        var username = "username123";
        var password = "secretPass1";
        var email = "test@example.com";
        var firstName = "john";
        var lastName = "smith";
        given(userRepository.existsByEmailIgnoreCase(anyString())).willReturn(false);
        given(userRepository.existsByUsernameIgnoreCase(anyString())).willReturn(false);
        given(userRepository.save(any())).will(returnsFirstArg());

        // when
        userService.createUser(username, password, email, firstName, lastName);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }
}
