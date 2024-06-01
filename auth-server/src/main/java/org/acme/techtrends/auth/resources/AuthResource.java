package org.acme.techtrends.auth.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.techtrends.auth.model.LoginRequest;
import org.acme.techtrends.auth.model.Tokens;
import org.acme.techtrends.auth.model.ValidationRequest;
import org.acme.techtrends.auth.services.AuthService;
import org.acme.techtrends.auth.services.TokenService;


@Path("/auth")
public class AuthResource {

    @Inject
    AuthService authenticationService;

    @Inject
    TokenService tokenService;

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
        String userType = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if( userType != null ) {

            String accessToken = tokenService.generateAccessToken(userType);
            String refreshToken = tokenService.generateRefreshToken(userType);

            return Response.ok(new Tokens(accessToken, refreshToken)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
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
                Tokens tokens = tokenService.refreshTokens(refreshToken);
                return Response.ok(tokens).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
