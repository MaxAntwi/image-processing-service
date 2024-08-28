package org.max.imageprocessingservice.service.image_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.max.imageprocessingservice.dto.GlobalResponse;
import org.max.imageprocessingservice.dto.ImageResponse;
import org.max.imageprocessingservice.entity.Image;
import org.max.imageprocessingservice.exception.ImageException;
import org.max.imageprocessingservice.repository.ImageRepository;
import org.max.imageprocessingservice.util.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public GlobalResponse getImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageResponse> imageResponses =  images.stream().map(this::getImageResponse).toList();
        return GlobalResponse
                .builder()
                .response("Image List")
                .responseCode(HttpStatus.OK.value())
                .images(imageResponses)
                .build();
    }

    public ImageResponse getImageResponse(Image image) {
        return ImageResponse
                .builder()
                .original(image.getOriginal())
                .filename(image.getFilename())
                .id(image.getId())
                .fileType(image.getFileType())
                .build();
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

    @Override
    public byte[] resizeImage(Long id, int width, int height) {
        Image image = imageRepository.findById(id).orElseThrow();
        try {
            byte[] imageData = ImageUtils.decompressImage(image.getImageData());
            return ImageUtils.resizeImage(imageData, width, height);
        }catch (Exception e) {
            throw new ImageException(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }
}
