package com.ecommerce.ecommerce.infra.security;

import com.ecommerce.ecommerce.model.Dto.ListUserDTO;


public record   DatosJWTToken(String jwTtoken , ListUserDTO userDetails) {
}
