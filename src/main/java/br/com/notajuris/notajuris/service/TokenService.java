package br.com.notajuris.notajuris.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.usuario.Usuario;

@Service
public class TokenService {

    private String key;

    private Algorithm alg;

    private final long tokenExpiration;
    private final long refreshTokenExpiration;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public TokenService(
        @Value("${api.jwt.key}") String key,
        @Value("${api.jwt.token.minutes}") long tokenExpiration,
        @Value("${api.jwt.refresh-token.hours}") long refreshTokenExpiration
        ){
        this.key = key;
        this.alg = Algorithm.HMAC512(key);
        this.tokenExpiration = tokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateToken(Usuario usuario){

        try {
            
            String token = JWT.create()
            .withIssuer("api-notajuris")
            .withSubject(usuario.getId().toString())
            .withIssuedAt(Instant.now())
            .withExpiresAt(generateExpiration(tokenExpiration))
            .sign(alg);

            return token;

        } catch (JWTCreationException e) {
            //TODO Exception Handling
            throw new RuntimeException(e.getMessage());
        }

    }

    public String generateToken(Usuario usuario, long expirationMinutes){

        try {
            
            String token = JWT.create()
            .withIssuer("api-notajuris")
            .withSubject(usuario.getId().toString())
            .withIssuedAt(Instant.now())
            .withExpiresAt(generateExpiration(expirationMinutes))
            .sign(alg);

            return token;

        } catch (JWTCreationException e) {
            //TODO Exception Handling
            throw new RuntimeException(e.getMessage());
        }

    }

    public String validateToken(String token){
        try {

            JWTVerifier verifier = JWT.require(alg)
            .withIssuer("api-notajuris")
            .build();

            String usuarioId = verifier.verify(token).getSubject();
            return usuarioId;

        } catch (TokenExpiredException e) {
            throw new BusinessException("token expirou", HttpStatus.BAD_REQUEST);
        } catch (JWTVerificationException e) {
            //TODO Exception Handling
            return null;
        }
    }

    private Instant generateExpiration(long minuto){
        return Instant.now().plusSeconds(minuto * 60);

    }

    public String generateRefreshToken(Usuario usuario){
        //gera string aleatoria
        int leftLimit = 48; //'0'
        int rightLimit = 122; //'z'
        int targetStringLength = 10;
        Random random = new Random();

        String refreshToken = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        //insere no redis
        redisTemplate.opsForValue().set(refreshToken, usuario.getId().toString());
        redisTemplate.expireAt(refreshToken, Instant.now().plus(refreshTokenExpiration, ChronoUnit.HOURS));
        return refreshToken;
    }

	public Integer validateRefreshToken(String refreshToken) {
		//procura token no redis
        String usuarioId = redisTemplate.opsForValue().getAndDelete(refreshToken);
        //se nao encontrar, lança exceção
        if(usuarioId == null){
            throw new BusinessException("refreshToken inválida", HttpStatus.NOT_FOUND);
        }
        //se encontrar, captura o id do usuário e remove o refreshToken do redis
        return Integer.parseInt(usuarioId);
	}
}
