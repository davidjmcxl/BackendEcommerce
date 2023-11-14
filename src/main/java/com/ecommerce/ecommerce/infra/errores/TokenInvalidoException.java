package com.ecommerce.ecommerce.infra.errores;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String message) {
        super(message);
    }
    public TokenInvalidoException() {
    }

    public TokenInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenInvalidoException(Throwable cause) {
        super(cause);
    }
}
