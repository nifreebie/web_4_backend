package web.web4_backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse")
public class ErrorResponse {
    @Schema(description = "Error message")
    public String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
