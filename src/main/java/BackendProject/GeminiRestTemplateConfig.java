package BackendProject;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class GeminiRestTemplateConfig {

    @Bean
    @Qualifier("geminiRestTemplate")
    public RestTemplate geminiRestTemplate() {
        return new RestTemplate();
    }
}