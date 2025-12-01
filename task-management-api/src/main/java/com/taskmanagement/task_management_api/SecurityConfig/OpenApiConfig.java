//package com.taskmanagement.task_management_api.SecurityConfig;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.info.License;
//import io.swagger.v3.oas.annotations.servers.Server;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Task Management Project",
//                version = "v1",
//                description = "REST API for managing employees and their tasks, including assignment, status tracking, and prioritization.",
//                contact = @Contact(
//                        name = "Task Management Team",
//                        email = "support@taskmanagement.local"
//                ),
//                license = @License(
//                        name = "Apache 2.0"
//                )
//        ),
//        servers = {
//                @Server(url = "/", description = "Default Server")
//        },
//        // Order in Swagger is based on tag names – we prefix with numbers
//        tags = {
//                @Tag(name = "1. Employee Management", description = "Operations related to employees"),
//                @Tag(name = "2. Task Management", description = "Operations related to tasks")
//        }
//)
//public class OpenApiConfig {
//}
//
//
//package com.taskmanagement.task_management_api.SecurityConfig;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.info.License;
//import io.swagger.v3.oas.annotations.servers.Server;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Task Management Project",
//                version = "v1",
//                description = "REST API for managing employees and their tasks, including assignment, status tracking, and prioritization.",
//                contact = @Contact(
//                        name = "Task Management Team",
//                        email = "support@taskmanagement.local"
//                ),
//                license = @License(
//                        name = "Apache 2.0"
//                )
//        ),
//        servers = {
//                @Server(url = "/", description = "Default Server")
//        },
//        // Order in Swagger is based on tag names – we prefix with numbers
//        tags = {
//                @Tag(name = "1. Employee Management", description = "Operations related to employees"),
//                @Tag(name = "2. Task Management", description = "Operations related to tasks")
//        }
//)
//public class OpenApiConfig {
//}

package com.taskmanagement.task_management_api.SecurityConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;  // ✅ for global security

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Management Project",
                version = "v1",
                description = "REST API for managing employees and tasks.",
                contact = @Contact(
                        name = "Task Management Team",
                        email = "support@taskmanagement.local"
                ),
                license = @License(name = "Apache 2.0")
        ),
        servers = {
                @Server(url = "/", description = "Default Server")
        },
        tags = {
                @Tag(name = "1. Employee Management", description = "Operations related to employees"),
                @Tag(name = "2. Task Management", description = "Operations related to tasks")
        },
        // 🔐 Make bearerAuth the DEFAULT for all endpoints
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}

