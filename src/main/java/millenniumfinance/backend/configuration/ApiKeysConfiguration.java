package millenniumfinance.backend.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApiKeysConfiguration {
  @Value("${api-key}")
  private String apiKey;
  
  @Value("${secret-key}")
  private String secretKey;
}
