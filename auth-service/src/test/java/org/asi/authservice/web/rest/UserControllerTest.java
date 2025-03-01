package org.asi.authservice.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asi.authservice.mapper.UserMapper;
import org.asi.authservice.model.User;
import org.asi.authservice.service.UserService;
import org.asi.authservice.utils.SpringSecurityWebTestConfig;
import org.asi.dtomodels.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = UserControllerTest.class)
@ContextConfiguration(classes = SpringSecurityWebTestConfig.class)
public class UserControllerTest {
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
    public void createNewUserShouldReturns200WhenValidInput() throws Exception {
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
}
