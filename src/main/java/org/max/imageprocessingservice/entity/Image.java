package org.max.imageprocessingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "Images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String fileType;
    private Long original;
    @Lob
    private byte[] imageData;
}

//Note: had to run the script below to alter image table
//ALTER TABLE images MODIFY COLUMN image_data LONGBLOB