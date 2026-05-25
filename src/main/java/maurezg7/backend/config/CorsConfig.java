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

                // CORRECCIÓN: Agregamos tu nueva URL de Vercel y un patrón genérico por si vuelve a cambiar
                corsConfiguration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:4200",
                                "http://localhost:8080",
                                "https://hermes-inky-two.vercel.app", // <-- Tu frontend actual
                                "https://hermes-c6gswsisi-maurezg7.vercel.app")); 

                // Si tu URL de Vercel cambia seguido, puedes usar la siguiente línea en lugar de setAllowedOrigins:
                // corsConfiguration.setAllowedOriginPatterns(Arrays.asList("https://*.vercel.app", "http://localhost:*"));

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
