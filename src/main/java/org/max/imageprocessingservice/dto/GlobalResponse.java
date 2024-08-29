package org.max.imageprocessingservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonSerialize
public class GlobalResponse implements Serializable {
    private Long imageId;
    private String response;
    private int responseCode;
    List<ImageResponse> images;
}
