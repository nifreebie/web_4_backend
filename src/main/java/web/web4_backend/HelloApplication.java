package web.web4_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import web.web4_backend.endpoints.PointController;
import web.web4_backend.endpoints.UserController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.HashSet;
import java.util.Set;


@OpenAPIDefinition(
//        tags = {
//                @Tag(name = "auth", description = "Authentication operations"),
//                @Tag(name = "points", description = "Point operations")
//        },
        info = @Info(
                title = "Web Lab 4 API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Isupov Nikita",
                        email = "i.nikitc@gmail.com"
                )
        ),
        servers = {
                @Server(
                        url = "/web4_backend-1.0-SNAPSHOT",
                        description = "Web Lab 4 Server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@ApplicationPath("/api")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ResteasyJackson2Provider.class);
        classes.add(UserController.class);
        classes.add(PointController.class);
        classes.add(CorsFilter.class);
        ResteasyJackson2Provider provider = new ResteasyJackson2Provider();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        provider.setMapper(objectMapper);
        return classes;
    }

}