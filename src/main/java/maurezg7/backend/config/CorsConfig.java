package maurezg7.backend.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
        @Bean
        public CorsFilter corsFilter() {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowCredentials(true);

                corsConfiguration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:4200",
                                "http://localhost:8080",
                                "https://hermes-c6gswsisi-maurezg7.vercel.app")); 

                // Simplificado para permitir las cabeceras estándar necesarias
                corsConfiguration.setAllowedHeaders(Arrays.asList(
                                "Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));

                corsConfiguration.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));

                corsConfiguration.setExposedHeaders(Arrays.asList(
                                "Authorization", "Content-Type"));

                corsConfiguration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfiguration);

                return new CorsFilter(source);
        }
}
