package com.homeapp.backend.services;

import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
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
import java.util.stream.Stream;

@Service
public class AdventureService {
    private static final String BASE_DIR = "src/main/adventures/";
    private final String GENERATED_FILE_NAME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "-generated_file_name";
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ErrorLogger errorLogger = new ErrorLogger();

    public void clearDirectory(String tileName) {
        Path directory = Path.of(BASE_DIR, tileName);
        infoLogger.log("Clearing files from directory: " + directory);
        checkDirectoryExists(directory);
        try (Stream<Path> paths = Files.walk(directory)) {
            paths
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            errorLogger.log("Failed to delete path: " + path + "\nError: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            errorLogger.log("Failed to clear directory: " + directory + "\nError: " + e.getMessage());
        }
    }

    public void uploadFile(MultipartFile file, String tileName) {
        infoLogger.log("Uploading file: " + file.getOriginalFilename() + "\nTo: " + tileName);
        Path directory = Path.of(BASE_DIR, tileName);
        checkDirectoryExists(directory);

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            errorLogger.log("Uploaded file has no name");
            originalName = GENERATED_FILE_NAME;
        }

        // Keep only the final filename and remove all whitespace characters.
        String safeFileName = Path.of(originalName).getFileName().toString().replaceAll(" ", "_");
        Path destination = directory.resolve(safeFileName);
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            infoLogger.log("Saved uploaded file to: " + destination.toAbsolutePath());
        } catch (IOException e) {
            errorLogger.log("Error while uploading file\n" + e.getMessage());
        }
    }

    private void checkDirectoryExists(Path path) {
        if (Files.isDirectory(path)) {
            infoLogger.log("Directory already exists: " + path);
        } else {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                errorLogger.log("Failed to create directory: " + path + "\nError: " + e.getMessage());
            }
        }
    }

    public ArrayList<Resource> getFiles(String directory) {
        ArrayList<Resource> files = new ArrayList<>();
        Path directoryPath = Path.of(BASE_DIR + directory);
        infoLogger.log("Getting files from directory: " + directory);
        checkDirectoryExists(directoryPath);
        try {
            Files.list(directoryPath).forEach(file -> {
                Resource resource = null;
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
            errorLogger.log(e.getMessage());
        }
        return files;
    }

    public Resource getSpecificFile(String filePath) {
        infoLogger.log("Getting file: " + filePath);
        Path basePath = Path.of(BASE_DIR).toAbsolutePath().normalize();
        Path requestedPath = basePath.resolve(filePath).normalize();
        Resource resource;
        try {
            resource = new UrlResource(requestedPath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                warnLogger.log("Requested file does not exist or is unreadable: " + requestedPath);
                resource = createDummyFile();
                return resource;
            }
        } catch (MalformedURLException e) {
            errorLogger.log("Invalid file path requested: " + filePath + "\nError: " + e.getMessage());
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
            errorLogger.log("Failed to create or access dummy file: " + requestedPath.toString() + "\nError: " + e.getMessage());
        }
        return resource;
    }
}