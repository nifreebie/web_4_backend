package web.web4_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterRequest")
public class RegisterRequest {
    @Schema(required = true, example = "user123")
    public String username;
    @Schema(required = true, example = "password123")
    public String password;
}
