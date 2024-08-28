package org.max.imageprocessingservice.service.image_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.max.imageprocessingservice.dto.GlobalResponse;
import org.max.imageprocessingservice.entity.Image;
import org.max.imageprocessingservice.exception.ImageException;
import org.max.imageprocessingservice.repository.ImageRepository;
import org.max.imageprocessingservice.util.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public GlobalResponse uploadImage(MultipartFile file) {
        try {
            var image = imageRepository.save(Image
                    .builder()
                    .filename(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes()))
                    .build());
            return GlobalResponse
                    .builder()
                    .response("Image Uploaded")
                    .imageId(image.getId())
                    .responseCode(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ImageException(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @Override
    public byte[] downloadImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow();
        try {
            return ImageUtils.decompressImage(image.getImageData());
        } catch (Exception e) {
            throw new ImageException(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }
}
