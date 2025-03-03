package org.asi.authservice.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asi.authservice.mapper.UserMapper;
import org.asi.authservice.model.User;
import org.asi.authservice.service.UserService;
import org.asi.authservice.utils.SpringSecurityWebTestConfig;
import org.asi.dtomodels.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = UserControllerTest.class)
@ContextConfiguration(classes = SpringSecurityWebTestConfig.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @WithAnonymousUser
    @Test
    void createNewUserShouldReturns200WhenValidInput() throws Exception {
        var inputUser = UserDTO.builder()
                .email("test@example.com")
                .username("username")
                .password("123456")
                .firstName("Trung")
                .lastName("Hieu")
                .build();

        given(userService.createUser(any())).willReturn(new User());
        given(userMapper.toDTO(any())).willReturn(new UserDTO());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
        verify(userService, times(1)).createUser(any());
    }

    @WithAnonymousUser
    @ParameterizedTest
    @MethodSource("invalidNewUsersSource")
    void createNewUserShouldReturns400WhenInvalidInput(UserDTO input) throws Exception {
        // given (in method source)
        // when + then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
        verify(userService, times(1)).createUser(any());
    }

    private static Stream<Arguments> invalidNewUsersSource() {
        return Stream.of(
                Arguments.of(new UserDTO("", "", "", "", "", "")),
                Arguments.of(new UserDTO(null, null, null, null, null, null)),
                Arguments.of(new UserDTO("", "t", "12", "@dsa", "@x-1", "email@.pl")),
                Arguments.of(new UserDTO("", "username", "password", "s", "s", "email@example.com")),
                Arguments.of(new UserDTO("", "userna_2@me", "password", "aaaa", "ssss", "email@example.com"))
        );
    }
}
