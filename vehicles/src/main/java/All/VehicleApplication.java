package All;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class VehicleApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleApplication.class,args);

    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    @Bean(name="pricing")
    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }
}
