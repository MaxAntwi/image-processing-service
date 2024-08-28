package org.max.imageprocessingservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GlobalResponse {
    private Long imageId;
    private String response;
    private int responseCode;
}
