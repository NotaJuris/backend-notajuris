package br.com.notajuris.notajuris.infra.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.notajuris.notajuris.model.usuario.Usuario;

@Component
public class JwtService {

    String key;

    private Algorithm alg;
    private static int EXPIRATION_MINUTES = 30;

    public JwtService(@Value("${api.jwt.key}") String key){
        this.key = key;
        this.alg = Algorithm.HMAC512(key);
    }

    public String generateToken(Usuario usuario){

        try {
            
            String token = JWT.create()
            .withIssuer("api-notajuris")
            .withSubject(usuario.getId().toString())
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plusSeconds(EXPIRATION_MINUTES * 60))
            .sign(alg);

            return token;

        } catch (JWTCreationException e) {
            //TODO Exception Handling
            throw new RuntimeException(e.getMessage());
        }

    }

    public Integer validateToken(String token){
        try {

            JWTVerifier verifier = JWT.require(alg)
            .withIssuer("api-notajuris")
            .build();

            String decoded = verifier.verify(token).getSubject();
            return Integer.parseInt(decoded);
            
        } catch (JWTVerificationException e) {
            //TODO Exception Handling
            throw new RuntimeException(e.getMessage());
        }
    }
}
