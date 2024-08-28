package org.max.imageprocessingservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GlobalResponse {
    private Long imageId;
    private String response;
    private int responseCode;
    List<ImageResponse> images;
}
