package org.max.imageprocessingservice.repository;

import org.max.imageprocessingservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
