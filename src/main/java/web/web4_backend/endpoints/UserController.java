package web.web4_backend.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.web4_backend.dto.request.LoginRequest;
import web.web4_backend.dto.request.RegisterRequest;
import web.web4_backend.dto.response.AuthenticateResponse;
import web.web4_backend.dto.response.ErrorResponse;
import web.web4_backend.model.User;
import web.web4_backend.security.JwtUtil;
import web.web4_backend.service.UserService;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "user")
public class UserController {
    @EJB
    private UserService userService;


    @POST
    @Path("/login")
    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(schema = @Schema(implementation = AuthenticateResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Authentication failed"
    )
    public Response login(LoginRequest request) {
        try {
            User user = userService.authenticate(request.username, request.password);
            String token = JwtUtil.generateToken(user.getId(), user.getUsername());
            return Response.ok(new AuthenticateResponse(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Invalid credentials"))
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Operation(
            summary = "Register new user",
            description = "Creates new user and returns JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Registration successful",
            content = @Content(schema = @Schema(implementation = AuthenticateResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Registration failed"
    )
    public Response register(RegisterRequest request) {
        try {
            User user = userService.register(request.username, request.password);
            String token = JwtUtil.generateToken(user.getId(), user.getUsername());
            return Response.ok(new AuthenticateResponse(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/getAll")
    public Response getAllUsers(){
        List<User> users = userService.findAll();
        return Response.ok(users).build();
    }
}
