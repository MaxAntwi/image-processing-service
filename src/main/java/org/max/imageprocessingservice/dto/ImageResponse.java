package org.max.imageprocessingservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponse {
    private Long id;
    private String filename;
    private String fileType;
    private Long original;
}
