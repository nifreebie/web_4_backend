package web.web4_backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthResponse")
public class AuthenticateResponse {
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    public String token;

    public AuthenticateResponse(String token) {
        this.token = token;
    }
}
