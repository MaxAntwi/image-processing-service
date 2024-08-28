package org.max.imageprocessingservice.service.image_service;

import org.max.imageprocessingservice.dto.GlobalResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    GlobalResponse uploadImage(MultipartFile file);

    byte[] downloadImage(Long id);
}
