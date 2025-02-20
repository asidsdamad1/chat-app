package org.asi.chatmessagesservice.security;

import org.asi.authutils.security.SecurityUserDetailsImpl;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SecurityUtils {

    public static Mono<String> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getPrincipal())
                .cast(SecurityUserDetailsImpl.class)
                .map(SecurityUserDetailsImpl::getId);
    }


}
