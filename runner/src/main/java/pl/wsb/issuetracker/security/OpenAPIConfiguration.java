package pl.wsb.issuetracker.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openApiInformation() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Localhost Server URL");

        Contact contact = new Contact()
                .email("m.karczewski1985@gmail.com")
                .name("Maciej Karczewski");

        Info info = new Info()
                .contact(contact)
                .description("Simple ticketing application")
                .title("Issue Tracker")
                .version("V1.0")
                .license(new License().name("MIT"));

        return new OpenAPI().info(info).addServersItem(localServer);
    }

}
