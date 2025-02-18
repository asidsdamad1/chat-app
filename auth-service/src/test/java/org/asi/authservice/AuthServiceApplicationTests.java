package org.asi.authservice;


import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.security.SecurityUserDetailsImpl;
import org.asi.authservice.service.impl.UserServiceImpl;
import org.asi.dtomodels.UserDTO;
import org.asi.exceptionutils.AlreadyExistsException;
import org.asi.exceptionutils.InvalidDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

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
        given(securityUserDetails.getId()).willReturn("123e4567-e89b-12d3-a456-426614174000");
    }

    @Test
    public void shouldCreateUser() {

        // given
        UserDTO userDTO = UserDTO.builder()
                .username("username123")
                .password("secretPass1")
                .email("test@example.com")
                .firstName("john")
                .lastName("smith")
                .build();
        given(userRepository.existsByEmailIgnoreCase(userDTO.email())).willReturn(false);
        given(userRepository.existsByUsernameIgnoreCase(userDTO.username())).willReturn(false);
        given(passwordEncoder.encode(userDTO.password())).willReturn("hashedPassword");
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));


        // when
        userService.createUser(userDTO);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenEmailAlreadyExists() {

        // given
        UserDTO userDTO = UserDTO.builder()
                .username("username123")
                .password("secretPass1")
                .email("test@example.com")
                .firstName("john")
                .lastName("smith")
                .build();
        given(userRepository.existsByEmailIgnoreCase(userDTO.email())).willReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenUsernameAlreadyExists() {

        // given
        UserDTO userDTO = UserDTO.builder()
                .username("username123")
                .password("secretPass1")
                .email("test@example.com")
                .firstName("john")
                .lastName("smith")
                .build();
        given(userRepository.existsByUsernameIgnoreCase(userDTO.username())).willReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenUsernameIsNull() {

        assertThrows(InvalidDataException.class, () -> userService.createUser(new UserDTO()));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenUsernameIsBlank() {
        var user = new UserDTO();
        user.username("      ");

        assertThrows(InvalidDataException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsBlank() {
        var user = new UserDTO();
        user.password("      ");

        assertThrows(InvalidDataException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any(User.class));
    }
}
