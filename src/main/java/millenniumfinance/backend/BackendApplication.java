package millenniumfinance.backend;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import millenniumfinance.backend.configuration.ApiKeysConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {
  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }
  
  @Bean
  public BinanceApiRestClient binanceApiRestClient(ApiKeysConfiguration apiKeysConfiguration) {
    System.out.println(apiKeysConfiguration.getApiKey());
    System.out.println(apiKeysConfiguration.getSecretKey());
    BinanceApiClientFactory binanceApiClientFactory = BinanceApiClientFactory
        .newInstance(apiKeysConfiguration.getApiKey(), apiKeysConfiguration.getSecretKey());
    return binanceApiClientFactory.newRestClient();
  }
  
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
  
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
      }
    };
  }
  
}
