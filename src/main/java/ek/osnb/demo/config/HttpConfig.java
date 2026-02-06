package ek.osnb.demo.config;

import ek.osnb.demo.openai.DatamatikerGptClient;
import ek.osnb.demo.todos.TodosClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "todos", types = {TodosClient.class})
@ImportHttpServices(group = "openai", types = {DatamatikerGptClient.class})
public class HttpConfig {

    private final String token;
    private final Logger log = LoggerFactory.getLogger(HttpConfig.class);

    public HttpConfig(@Value("${openai_api_key}") String token) {
        if (token == null || token.isBlank()) {
            log.warn("NO API KEY PROVIDED, OPENAI CLIENTS WILL NOT WORK");
        }
        this.token = token;
    }

    @Bean
    RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> {
            groups.filterByName("todos")
                    .forEachClient((_, builder) -> {
                        builder.baseUrl("https://jsonplaceholder.typicode.com");
                    });
            groups.filterByName("openai")
                    .forEachClient((_, builder) -> {
                        builder.baseUrl("https://api.openai.com/v1")
                                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.token);
                    });
        };
    }
}
