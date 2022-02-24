package com.proiect.SCD.utils;

import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
    public static BufferedImage resizeImageWidthChange(BufferedImage originalImage,int convertImageWidth, int convertImageHeight, int type){
        BufferedImage resizedImage = new BufferedImage(convertImageWidth, convertImageHeight, type);
        //Rendering image with reduced memory size
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, convertImageWidth, convertImageHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
        int imageHeight=originalImage.getHeight();
        int imageWidth=originalImage.getWidth();
        BufferedImage resizedImage = resizeImageWidthChange(originalImage,imageHeight,imageWidth,type);
        return resizedImage;
    }

}
