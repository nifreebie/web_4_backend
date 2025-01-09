package web.web4_backend.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.web4_backend.dto.request.AddPointRequest;
import web.web4_backend.dto.response.ErrorResponse;
import web.web4_backend.model.Point;
import web.web4_backend.security.JwtUtil;
import web.web4_backend.service.PointService;

import java.util.List;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "points")
@SecurityRequirement(name = "bearerAuth")
public class PointController {
    @EJB
    private PointService pointService;

    @POST
    @Path("/add")
    @Operation(
            summary = "Add new point",
            description = "Adds a new point and checks if it hits the area"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Point added successfully",
            content = @Content(schema = @Schema(implementation = Point.class))
    )
    public Response addPoint(
            @Context HttpHeaders headers,
            @Parameter(schema = @Schema(implementation = AddPointRequest.class))
            AddPointRequest request
    ) {
        try {
            String token = extractToken(headers);
            Long userId = JwtUtil.getUserIdFromToken(token);

            Point point = pointService.addPoint(
                    request.x,
                    request.y,
                    request.r,
                    userId
            );

            return Response.ok(point).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("get")
    @Operation(
            summary = "Get all points",
            description = "Returns all points for the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Points retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Point.class)))
    )
    public Response getPoints(@Context HttpHeaders headers) {
        try {
            String token = extractToken(headers);
            Long userId = JwtUtil.getUserIdFromToken(token);

            List<Point> points = pointService.getUserPoints(userId);
            return Response.ok(points).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/clear")
    @Operation(
            summary = "Clear all points",
            description = "Deletes all points for the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Points cleared successfully"
    )
    public Response clearPoints(@Context HttpHeaders headers) {
        try {
            String token = extractToken(headers);
            Long userId = JwtUtil.getUserIdFromToken(token);

            pointService.clearUserPoints(userId);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }


    private String extractToken(HttpHeaders headers) {
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No valid authorization header found");
        }
        return authHeader.substring(7);
    }

}