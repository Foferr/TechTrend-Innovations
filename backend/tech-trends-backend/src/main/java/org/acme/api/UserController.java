package org.acme.api;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.MessageDTOs.MessagePostDTO;
import org.acme.DTO.UserDTOs.UserPostDTO;
import org.acme.DTO.decryptionDTOs.DecryptedUserDTO;
import org.acme.model.User;
import org.acme.service.UserService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    // @GET
    // @RolesAllowed({"admin", "base_user"})
    // public List<User> getAllUsers(@HeaderParam("X-Dev-Key") String apiKey) {
    //     return userService.getAllUsers();

    // }

    @GET
//    @RolesAllowed({"admin", "base_user"})
    @PermitAll
    public List<DecryptedUserDTO> getAllUsers() {
        return userService.getAllUsers();

    }


    @POST
    @Path("/registerUser")
    @PermitAll
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserPostDTO.class)
    ))
    @Transactional
    public Response registerUser(User user) {
        try {
            if(userService.emailExists(user.getEmail())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"Email already in use\"}")
                        .build();
            }
            // Assuming userService.createUser fetches the user from the database first
            userService.createUser(user);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"User created successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")

                    .build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<DecryptedUserDTO> userDTOOptional = userService.getUserById(id);
        if (userDTOOptional.isPresent()) {
            return Response.ok(userDTOOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }
    }

    @PUT
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/editUser/{id}")
//    @RolesAllowed({"admin", "base_user"})
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserPostDTO.class)
    ))
    public Response updateUser(@PathParam("id") Long id, User user) {
        try {
            Optional<DecryptedUserDTO> existingUserOptional = userService.getUserById(id);
            if (existingUserOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"User not found\"}")
                        .build();
            }

            userService.updateUser(id, user);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"User updated successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/deleteUser/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            userService.deleteUser(id);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"User deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
