package com.github.hronom.test.spring.security.configs.custom.objects;

import com.github.hronom.test.spring.security.components.CustomAuthenticationProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {
    private final CustomAuthenticationProvider authenticationProvider =
        new CustomAuthenticationProvider();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationManager.authenticate");
        return authenticationProvider.authenticate(authentication);
    }
}