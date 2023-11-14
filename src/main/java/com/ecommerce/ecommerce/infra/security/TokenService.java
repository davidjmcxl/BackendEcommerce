package com.ecommerce.ecommerce.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.ecommerce.infra.errores.TokenInvalidoException;
import com.ecommerce.ecommerce.model.Dto.ListUserDTO;
import com.ecommerce.ecommerce.model.Dto.RenewTokenDto;
import com.ecommerce.ecommerce.model.entities.User;
import com.ecommerce.ecommerce.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;
    @Autowired
    private UserRepository userRepository;
    public String generarToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("ecommerce")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }
    public RenewTokenDto renovarToken(String tokenJwt) {

        if (tokenJwt == null || tokenJwt.isEmpty()) {
            throw new IllegalArgumentException("El token es nulo o vacío. Debe proporcionar un token válido.");
        }
        var token = tokenJwt.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        DecodedJWT decodedJWT = null;


        try {

            decodedJWT = JWT.decode(token);

            // Verificar si el token ha expirado o no
            if (decodedJWT.getExpiresAt().before(new Date())) {
                throw new RuntimeException("El token ha expirado y no puede ser renovado.");
            }

            // Extraer las reclamaciones necesarias del token original
            String username = decodedJWT.getSubject();

            Long userId = decodedJWT.getClaim("id").asLong();
            var userInfo = this.userRepository.findByEmail(username);
            var tokenRefresh = JWT.create()
                    .withIssuer("ecommerce")
                    .withSubject(username)
                    .withClaim("id", userId)
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
            // Generar un nuevo token con las mismas reclamaciones pero con una nueva fecha de expiración
            return new RenewTokenDto(tokenRefresh, new ListUserDTO((User) userInfo));


        } catch (JWTVerificationException  exception) {
            throw new IllegalArgumentException("El token es inválido o no se pudo decodificar.");
        } catch (JWTCreationException exception) {
            throw new RuntimeException("No se pudo crear un nuevo token.");
        }
    }

    public String getSubject(String token) {

        if (token == null) {
            throw new RuntimeException("El objeto 'verifier' no está inicializado.");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer("alura")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTDecodeException exception) {

            throw new TokenInvalidoException("token invalido");

        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

}
