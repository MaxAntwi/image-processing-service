package org.max.imageprocessingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class InvalidTokenException extends RuntimeException {
    private final int statusCode;

    public InvalidTokenException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
