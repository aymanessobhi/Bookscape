package com.essobhi.bookscape.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Essobhi",
                email = "aymaessobhi@gmail.com"
        ),
        description = "OpenApi documentation for Spring Security",
        title = "OpenApi specification - Essobhi",
        version = "1.0",
        license = @License(
                name = "Licence name",
                url = "https://some-url.com"
        ),
        termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api"
                ),
                @Server(
                        description = "PROD ENV"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        })
public class OpenApiConfig {
}
