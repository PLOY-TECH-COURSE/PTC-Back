package org.plteco.ploytechcourse.infrastructure.persistence;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Tech Course API", version = "v1"))
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP, // SecuritySchemeType 사용
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}