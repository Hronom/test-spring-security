package com.github.hronom.test.spring.security.components;

import com.github.hronom.test.spring.security.configs.custom.objects.CustomUser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private Logger logger = LogManager.getLogger();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());

        logger.info("username:" + username);
        // Don't log passwords in real app.
        logger.info("password:" + password);

        if (username.equals("admin") && password.equals("admin")) {
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return createUser(username, authorities);
        }
        else if (username.equals("user") && password.equals("user")) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return createUser(username, authorities);
        } else {
            throw new BadCredentialsException("Authentication fails!");
        }
    }

    @Override
    public boolean supports(Class aClass) {
        // To indicate that this AuthenticationProvider can handle the auth request. since there's
        // currently only one way of logging in, always return true.
        return true;
    }

    private UsernamePasswordAuthenticationToken createUser(
        String username,
        Collection<? extends GrantedAuthority> authorities
    ) {
        CustomUser user = new CustomUser(username, authorities);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
