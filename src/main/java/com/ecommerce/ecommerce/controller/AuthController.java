package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.infra.security.DatosJWTToken;
import com.ecommerce.ecommerce.model.Dto.AuthDTO;
import com.ecommerce.ecommerce.model.Dto.RenewTokenDto;
import com.ecommerce.ecommerce.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "bearer-key")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping
    public ResponseEntity<DatosJWTToken> authUser(@RequestBody @Valid AuthDTO authDTO) {
       return authService.LoginAuth(authDTO);

    }
    @GetMapping("/renew")
    public ResponseEntity<RenewTokenDto> refreshToken(@RequestHeader("Authorization") String token) {
        var renewTokenDto= authService.refreshToken(token);

      return  ResponseEntity.ok(renewTokenDto);

    }
}
