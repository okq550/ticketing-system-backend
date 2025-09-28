package org.okq550.ticketing.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

// Final prevents inheritance
// Private constructor prevents object instatination
// Static method can be called without creating instances
// Single responsibility, Only used to convert JWT-to-UUID
public final class JwtUtil {
    private JwtUtil(){}

    public static UUID parseUUID(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
