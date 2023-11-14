package com.ecommerce.ecommerce.infra.errores;

public class IntegrityValidation extends RuntimeException {
public IntegrityValidation(String s){
    super(s);
}
}
