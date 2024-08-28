package org.max.imageprocessingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ImageException extends RuntimeException {
    private final int statusCode;

    public ImageException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
