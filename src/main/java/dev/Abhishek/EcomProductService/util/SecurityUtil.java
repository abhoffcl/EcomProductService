package dev.Abhishek.EcomProductService.util;

import dev.Abhishek.EcomProductService.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {

    private static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static UUID getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }

    public static String getCurrentUserName() {
        return getCurrentUserDetails().getUsername();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUserDetails().getEmail();
    }

    public static String getCurrentUserPhoneNumber() {
        return getCurrentUserDetails().getPhoneNumber();
    }
}
