package com.fitness.courses.configuration.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Fitness Courses Platform Api",
                description = "Fitness Courses Platform Api",
                version = "0.0.1",
                contact = @Contact(
                        name = "Vshivtsev Daniil",
                        email = "danya.vshivtsev@mail.ru"
                )

        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig
{
}
