package org.asi.authutils;

import org.asi.authutils.security.SecurityUserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUser() {
        var principal = (SecurityUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    public static String getCurrentUserPreferredUsername(){
        var principal = (SecurityUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

}
