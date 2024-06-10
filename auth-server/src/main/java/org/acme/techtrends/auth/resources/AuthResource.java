package org.acme.techtrends.auth.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.techtrends.auth.model.LoginRequest;
import org.acme.techtrends.auth.model.Tokens;
import org.acme.techtrends.auth.model.UserDataForToken;
import org.acme.techtrends.auth.model.ValidationRequest;
import org.acme.techtrends.auth.services.AuthService;
import org.acme.techtrends.auth.services.TokenService;
import org.acme.techtrends.auth.utils.EncryptionUtil;


@Path("/auth")
public class AuthResource {

    @Inject
    AuthService authenticationService;

    @Inject
    TokenService tokenService;

    @OPTIONS
    @Path("{path : .*}")
    public Response handlePreflight() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }


    @POST
    @Path("/validate-token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(ValidationRequest validationRequest) {
        // Here you would validate the token
        boolean isValid = tokenService.isAccessTokenInvalid(validationRequest.getToken());

        if (isValid) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        try {
            UserDataForToken userData = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            if( userData != null ) {

                String accessToken = tokenService.generateAccessToken(EncryptionUtil.decrypt(userData.getUserType()), userData.getUserId());
                String refreshToken = tokenService.generateRefreshToken(EncryptionUtil.decrypt(userData.getUserType()), userData.getUserId());

                return Response.ok(new Tokens(accessToken, refreshToken)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/logout")
    @RolesAllowed({"admin", "base_user"})
    public Response logout(
            @HeaderParam("Authorization") String accessToken,
            @HeaderParam("Refresh-Token") String refreshToken
    )
    {
        if (accessToken == null || accessToken.isEmpty() || refreshToken == null || refreshToken.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        tokenService.invalidateAccessToken(accessToken);
        tokenService.invalidateRefreshToken(refreshToken);

        return Response.ok("Logout successful").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/refresh")
    @RolesAllowed({"admin", "base_user"})
    public Response refreshTokens(@HeaderParam("Authorization") String refreshTokenHeader) {
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring("Bearer ".length()).trim();

            if (tokenService.validateRefreshToken(refreshToken)) {
                Long userId = tokenService.extractIdFromToken(refreshToken);
                String userType = tokenService.extractUserTypeFromToken(refreshToken);
                Tokens tokens = tokenService.refreshTokens(userType, userId);
                return Response.ok(tokens).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}

