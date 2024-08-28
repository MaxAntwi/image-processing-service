package org.max.imageprocessingservice.util;

import lombok.extern.slf4j.Slf4j;
import org.max.imageprocessingservice.exception.ImageException;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class ImageUtils {
    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressImage(byte[] image) {
        try {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(image);
            deflater.finish();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
            byte[] temp = new byte[BITE_SIZE];

            while (!deflater.finished()) {
                int count = deflater.deflate(temp);
                outputStream.write(temp, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ImageException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public static byte[] decompressImage(byte[] image) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(image);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
            byte[] temp = new byte[BITE_SIZE];
            while (!inflater.finished()) {
                int count = inflater.inflate(temp);
                outputStream.write(temp, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException | DataFormatException e) {
            log.error(e.getMessage());
            throw new ImageException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
