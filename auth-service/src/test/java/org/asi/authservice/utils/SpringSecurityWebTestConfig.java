package org.asi.authservice.utils;

import org.asi.authservice.model.Role;
import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.security.UserDetailServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    @Bean
    public UserDetailServiceImpl testUserDetailService() {
        var userRepositoryMock = mock(UserRepository.class);
        var user = User.builder()
                .id(UUID.fromString("1062d618-64f6-401c-ab7c-ef050eb6f4b2"))
                .username("test")
                .firstName("firstName")
                .lastName("lastName")
                .enabled(true)
                .password("password")
                .build();

        var role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        given(userRepositoryMock.findOneWithAuthoritiesByUsernameIgnoreCase(anyString())).willReturn(Optional.of(user));
        return new UserDetailServiceImpl(userRepositoryMock);
    }
}
