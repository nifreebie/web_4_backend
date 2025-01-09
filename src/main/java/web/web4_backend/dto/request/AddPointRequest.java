package web.web4_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AddPointRequest")
public class AddPointRequest {
    @Schema(required = true, example = "1.5", minimum = "-5", maximum = "3")
    public float x;
    @Schema(required = true, example = "2.0", minimum = "-5", maximum = "3")
    public float y;
    @Schema(required = true, example = "2.0", minimum = "1", maximum = "4")
    public float r;
}
