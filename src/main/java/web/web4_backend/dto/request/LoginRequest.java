package web.web4_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest")
public class LoginRequest {
    @Schema(required = true, example = "user123")
    public String username;
    @Schema(required = true, example = "password123")
    public String password;
}
