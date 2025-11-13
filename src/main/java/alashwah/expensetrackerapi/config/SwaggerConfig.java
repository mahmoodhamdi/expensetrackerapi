package alashwah.expensetrackerapi.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI expenseTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker API")
                        .description("Spring Boot REST API for managing expenses")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alashwah")
                                .email("support@alashwah.com")
                        )
                );
    }
}
