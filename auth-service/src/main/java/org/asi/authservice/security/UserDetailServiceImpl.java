package org.asi.authservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authservice.model.User;
import org.asi.authservice.repository.UserRepository;
import org.asi.authservice.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Fetching given user {}", username);

        User user = userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        return new CustomUserDetails(user);
    }
}
