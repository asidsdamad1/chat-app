package org.asi.authservice.utils;

import org.asi.authservice.mapper.UserMapper;
import org.asi.authservice.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestMocks {
    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public UserMapper userMapper() {
        return mock(UserMapper.class);
    }
}