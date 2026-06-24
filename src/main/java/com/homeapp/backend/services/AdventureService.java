package com.homeapp.backend.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.logging.Level.*;

@Service
public class AdventureService {
    Logger logger = Logger.getLogger(AdventureService.class.getName());
    private static final String BASE_DIR = "src/main/adventures/";
    private final String GENERATED_FILE_NAME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "-generated_file_name";

    public void clearDirectory(String tileName) {
        Path directory = Path.of(BASE_DIR, tileName);
        logger.log(INFO, "Clearing files from directory: " + directory);
        checkDirectoryExists(directory);
        try (Stream<Path> paths = Files.walk(directory)) {
            paths
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            logger.log(SEVERE, "Failed to delete path: " + path + "\nError: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            logger.log(SEVERE, "Failed to clear directory: " + directory + "\nError: " + e.getMessage());
        }
    }

    public void uploadFile(MultipartFile file, String tileName) {
        logger.log(INFO, "Uploading file: " + file.getOriginalFilename() + "\nTo: " + tileName);
        Path directory = Path.of(BASE_DIR, tileName);
        checkDirectoryExists(directory);

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            logger.log(SEVERE, "Uploaded file has no name");
            originalName = GENERATED_FILE_NAME;
        }

        // Keep only the final filename and remove all whitespace characters.
        String safeFileName = Path.of(originalName).getFileName().toString().replaceAll(" ", "_");
        Path destination = directory.resolve(safeFileName);
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            logger.log(INFO, "Saved uploaded file to: " + destination.toAbsolutePath());
        } catch (IOException e) {
            logger.log(SEVERE, "Error while uploading file\n" + e.getMessage());
        }
    }

    private void checkDirectoryExists(Path path) {
        if (Files.isDirectory(path)) {
            logger.log(INFO, "Directory already exists: " + path);
        } else {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.log(SEVERE, "Failed to create directory: " + path + "\nError: " + e.getMessage());
            }
        }
    }

    public ArrayList<Resource> getFiles(String directory) {
        ArrayList<Resource> files = new ArrayList<>();
        Path directoryPath = Path.of(BASE_DIR + directory);
        logger.log(INFO, "Getting files from directory: " + directory);
        checkDirectoryExists(directoryPath);
        try {
            Files.list(directoryPath).forEach(file -> {
                Resource resource;
                try {
                    resource = new UrlResource(file.toUri());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                if (resource.exists()) {
                    files.add(resource);
                }
            });
        } catch (IOException e) {
            logger.log(SEVERE, e.getMessage());
        }
        return files;
    }

    public Resource getSpecificFile(String filePath) {
        logger.log(INFO, "Getting file: " + filePath);
        Path basePath = Path.of(BASE_DIR).toAbsolutePath().normalize();
        Path requestedPath = basePath.resolve(filePath).normalize();
        Resource resource;
        try {
            resource = new UrlResource(requestedPath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                logger.log(WARNING, "Requested file does not exist or is unreadable: " + requestedPath);
                resource = createDummyFile();
                return resource;
            }
        } catch (MalformedURLException e) {
            logger.log(SEVERE, "Invalid file path requested: " + filePath + "\nError: " + e.getMessage());
            resource = createDummyFile();
            return resource;
        }
    }

    private Resource createDummyFile() {
        Resource resource = null;
        Path basePath = Path.of(BASE_DIR).toAbsolutePath().normalize();
        Path requestedPath = basePath.resolve("dummyImage-LonelyMountain.jpg").normalize();
        try {
            resource = new UrlResource(requestedPath.toUri());
        } catch (IOException e) {
            logger.log(SEVERE, "Failed to create or access dummy file: " + requestedPath + "\nError: " + e.getMessage());
        }
        return resource;
    }
}