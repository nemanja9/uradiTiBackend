package com.uraditi.backend.service;

import com.uraditi.backend.dto.enums.EntityType;
import com.uraditi.backend.exception.ApiExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {

    @Value("${files.default-path:java}")
    String filesPath;

    @Value("${files.img-max-size:java}")
    Long imgMaxFileSize;

    private final CategoryService categoryService;

    public void saveFile(String entityId, EntityType type, MultipartFile file) throws IOException {
        checkExtension(file);
        checkSize(file);
        checkEntity(entityId, type);

        var fileBytes = file.getBytes();
        String rootPath = filesPath + File.separator + type.name().toLowerCase();
        String imagePath = rootPath + File.separator + entityId;
        StringBuilder fileName = new StringBuilder().append("UPLOADED-").append(entityId);
        String extension = "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        try {
            Path path = Paths.get(imagePath);
            FileUtils.deleteDirectory(new File(path.toUri()));
            Files.createDirectories(path);
            File imageFile = File.createTempFile(fileName.toString(), extension, new File(imagePath));
            FileUtils.writeByteArrayToFile(imageFile, fileBytes);
        } catch (IOException e) {
            System.out.println("Failed to save file!");
            System.out.println(e.getMessage());
        }
    }

    public Map<String, Object> getFile(String entityId, EntityType type) {
        checkEntity(entityId, type);
        String fileName = null;
        byte[] image = null;

        try {
            String rootPath = filesPath + File.separator + type.name().toLowerCase();
            File imgDir = new File(rootPath + File.separator + entityId);
            if (imgDir.listFiles() == null) {
                throw ApiExceptionFactory.notFound("File couldn't be found");
            }
            for (File file : imgDir.listFiles()) {
                image = Files.readAllBytes(file.toPath().toAbsolutePath());
                fileName = file.getName();
            }

        } catch (IOException e) {
            System.out.println("Failed to get file!");
            System.out.println(e.getMessage());
        }
        return Map.of("fileName", fileName, "file", image);
    }


    /**
     * Checks if the given Entity with the given id exists
     *
     * @param entityId
     * @param type
     */
    private void checkEntity(String entityId, EntityType type) {
        switch (type) {
            case CATEGORY_EXPLORE:
                categoryService.getById(Long.parseLong(entityId));
                break;
            default:
                throw ApiExceptionFactory.badRequest("Unsupported entity type!");
        }
    }

    private void checkSize(MultipartFile file) {
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        if (StringUtils.equalsAny(extension, "jpg", "jpeg", "png")) { // image file
            if (file.getSize() > imgMaxFileSize) {
                throw ApiExceptionFactory.badRequest("File too big!");
            }
        }
    }

    private void checkExtension(MultipartFile file) {
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        if (!StringUtils.equalsAny(extension, "jpg", "jpeg", "png")) {
            throw ApiExceptionFactory.badRequest("File type unsupported!");
        }
    }


}
