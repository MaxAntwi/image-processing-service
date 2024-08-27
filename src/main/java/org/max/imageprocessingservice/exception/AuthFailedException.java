package org.max.imageprocessingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AuthFailedException extends RuntimeException {
    private final int statusCode;

    public AuthFailedException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
