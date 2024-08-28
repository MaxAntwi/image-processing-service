package org.max.imageprocessingservice.controller;

import lombok.RequiredArgsConstructor;
import org.max.imageprocessingservice.dto.GlobalResponse;
import org.max.imageprocessingservice.service.image_service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse> uploadImage(@RequestPart("image") MultipartFile image) {
        return new ResponseEntity<>(imageService.uploadImage(image), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id) {
        byte[] image = imageService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK.value()).contentType(MediaType.IMAGE_PNG).body(image);
    }

}
