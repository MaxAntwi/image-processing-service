package org.max.imageprocessingservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonSerialize
public class ImageResponse implements Serializable {
    private Long id;
    private String filename;
    private String fileType;
    private Long original;
}
