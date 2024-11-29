package br.com.notajuris.notajuris.infra.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf((csrf) -> csrf.disable())
            .headers((header) -> header.frameOptions(frameOption -> frameOption.disable()))
            .authorizeHttpRequests((request) -> 
                request
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/auth/*/refresh").permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/usuarios/").hasAnyRole("ADMIN", "SUPERADMIN")
                    .requestMatchers(HttpMethod.POST, "/v1/atividades/").hasAnyRole("ALUNO")
                    .requestMatchers(HttpMethod.GET, "/v1/atividades").hasAnyRole("ORIENTADOR", "ADMIN", "SUPERADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/v1/atividades/*/status").hasAnyRole("ORIENTADOR", "ADMIN", "SUPERADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/v1/atividades/*/solicitar-reenvio").hasAnyRole("ORIENTADOR", "ADMIN", "SUPERADMIN")
                    .anyRequest().authenticated()
            )
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(exceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter, ExceptionHandlerFilter.class);

        return http.build();
    } 

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter(){
        return new ExceptionHandlerFilter();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
