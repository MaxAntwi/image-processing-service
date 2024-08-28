package org.max.imageprocessingservice.controller;

import lombok.RequiredArgsConstructor;
import org.max.imageprocessingservice.dto.GlobalResponse;
import org.max.imageprocessingservice.service.image_service.ImageService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<GlobalResponse> getImage() {
        return new ResponseEntity<>(imageService.getImages(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id) {
        byte[] image = imageService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK.value()).contentType(MediaType.IMAGE_PNG).body(image);
    }

    @GetMapping("{id}/resize")
    public ResponseEntity<?> resizeImage(@PathVariable Long id, @RequestParam Integer width, @RequestParam Integer height) {
        byte[] image = imageService.resizeImage(id, width, height);
        return ResponseEntity.status(HttpStatus.OK.value()).contentType(MediaType.IMAGE_PNG).body(image);
    }

    @GetMapping("{id}/rotate")
    public ResponseEntity<?> rotateImage(@PathVariable Long id, @RequestParam double angle) {
        byte[] image = imageService.rotateImage(id, angle);
        return ResponseEntity.status(HttpStatus.OK.value()).contentType(MediaType.IMAGE_PNG).body(image);
    }
}
