package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.infra.security.DatosJWTToken;
import com.ecommerce.ecommerce.infra.security.TokenService;
import com.ecommerce.ecommerce.model.Dto.AuthDTO;
import com.ecommerce.ecommerce.model.Dto.ListUserDTO;
import com.ecommerce.ecommerce.model.Dto.RenewTokenDto;
import com.ecommerce.ecommerce.model.entities.User;
import com.ecommerce.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<DatosJWTToken> LoginAuth(AuthDTO authDTO) {
        try{
            Authentication authToken = new UsernamePasswordAuthenticationToken(authDTO.email(),
                    authDTO.password());

            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((User) usuarioAutenticado.getPrincipal());
            var detailUser= userRepository.findByEmail(authDTO.email());

            return ResponseEntity.ok(new DatosJWTToken(JWTtoken,new ListUserDTO((User) detailUser)));
        }catch (BadCredentialsException e) {
            throw new ValidationException("Credenciales de inicio incorrectas");
        }
    }
    public RenewTokenDto refreshToken( String token) {
        var renewTokenDto= tokenService.renovarToken(token);
        return renewTokenDto;
    }

}
