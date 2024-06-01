package org.acme.techtrends.auth.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.techtrends.auth.model.Tokens;
import org.eclipse.microprofile.jwt.Claims;

import java.io.StringReader;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class TokenService {

    @Context
    SecurityContext securityContext;

    private Set<String> invalidatedAccessTokens = new HashSet<>();
    private Set<String> invalidatedRefreshTokens = new HashSet<>();

    public String generateAccessToken(String role) {
//        Set<String> roles = new HashSet<>(
//                Arrays.asList("admin", "base_user")
//        );

        return Jwt.issuer("nova-jwt")
                .subject("access-token")
                .groups(role)
                .expiresAt(
                        System.currentTimeMillis() + (1000 * 60 * 60)
                )
                .claim("type", "access")
                .sign();
    }

    public String generateRefreshToken(String role) {
        return Jwt.issuer("nova-jwt")
                .subject("refresh-token")
                .groups(role)
                .expiresAt(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))
                .claim("type", "refresh")
                .sign();
    }

    public Tokens refreshTokens(String role) {
        String accessToken = generateAccessToken(role);
        String refreshToken = generateRefreshToken(role);
        return new Tokens(accessToken, refreshToken);
    }

    public void invalidateAccessToken(String accessToken) {
        invalidatedAccessTokens.add(accessToken);
    }

    public void invalidateRefreshToken(String refreshToken) {
        invalidatedRefreshTokens.add(refreshToken);
    }

    public boolean isAccessTokenInvalid(String accessToken) {
        return !invalidatedAccessTokens.contains(accessToken);
    }

    public boolean isRefreshTokenInvalid(String refreshToken) {
        return invalidatedRefreshTokens.contains(refreshToken);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return "nova-jwt".equals(extractIssuerFromToken(refreshToken));
    }

    public static String extractIssuerFromToken(String token) {
        String[] parts = token.split("\\.");

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        try (JsonReader reader = Json.createReader(new StringReader(payload))) {
            JsonObject jsonPayload = reader.readObject();

            return jsonPayload.getString(Claims.iss.name());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String extractRoleFromToken(String token) {
        // Split the token into its parts (header, payload, signature)
        String[] parts = token.split("\\.");

        // Extract the payload (part 1)
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        // Parse the payload as JSON
        try (JsonReader reader = Json.createReader(new StringReader(payload))) {
            JsonObject jsonPayload = reader.readObject();

            // Get the role claim from the payload
            return jsonPayload.getString(Claims.groups.name());
        } catch (Exception e) {
            // Handle any exceptions (e.g., parsing error, missing claim)
            e.printStackTrace();
            return null;
        }
    }
}
