package org.max.imageprocessingservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private String message;
    private String name;
    private String username;
    private int statusCode;
    private String token;
}
