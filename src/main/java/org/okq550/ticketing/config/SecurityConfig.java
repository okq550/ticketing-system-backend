package org.okq550.ticketing.config;

import org.okq550.ticketing.filters.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserProvisioningFilter userProvisioningFilter,
                                           JwtAuthenticationConverter jwtAuthenticationConverter) throws
            Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorize -> authorize
                                // Permit this route, Making it public.
                                .requestMatchers(HttpMethod.GET, "/api/v1/published-events/**").permitAll()
                                .requestMatchers("/api/v1/events").hasRole("ORGANIZER")
                                .requestMatchers("/api/v1/ticket-validations").hasRole("STAFF")
                                // Catch all routes rule
                                .anyRequest().authenticated())
                // Disable CSRF
                .csrf(csrf -> csrf.disable())
                // Sessions are stateless
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add our custom filter
                .oauth2ResourceServer(
                        oauth2 ->
                                oauth2.jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
