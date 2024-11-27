package br.com.notajuris.notajuris.unittests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.com.notajuris.notajuris.service.TokenService;

@SpringBootTest
public class TokenServiceTest {

    @TestConfiguration
    static class tokenServiceTestConfig{

            @Value("${api.jwt.key}")
            String jwtKey;

            @Value("${api.jwt.token.minutes}") 
            long tokenExpiration;

            @Value("${api.jwt.refresh-token.minutes}") 
            long refreshTokenExpiration;

        @Bean
        public TokenService tokenService(){
            return new TokenService(jwtKey, tokenExpiration, refreshTokenExpiration);
        }

    }
    
}
