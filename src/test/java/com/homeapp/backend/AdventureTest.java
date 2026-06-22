package com.homeapp.backend;

import com.homeapp.backend.services.AdventureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Image test.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdventureTest {

    @Autowired
    private AdventureService adventureService;
    private static final String tile = "test/9-1";

    /**
     * Sets up testing suite.
     * Clears directory to ensure clean run for each test
     */
    @BeforeEach
    public void setup() {
        adventureService.clearDirectory(tile);
    }

    /**
     * Test that getFiles() will return 4 files from the directory Test/01.
     */
    @Test
    public void test_That_List_of_Files_Returned() {
        ArrayList<Resource> files = adventureService.getFiles("test/01");
        assertEquals(4, files.size());
    }

    /**
     * Test that getFiles() will return an empty list if called.
     */
    @Test
    public void test_That_Empty_List_of_Files_Returned() {
        assertTrue(adventureService.getFiles(tile).isEmpty());
    }

    /**
     * Test an empty directory is created and returned when getFiles() is called.
     */
    @Test
    public void test_That_Directory_Created_By_getFiles() {
        String directory = "src/main/adventures/test/9-1";
        Path path = Path.of(directory);
        assertFalse(Files.isDirectory(path));
        adventureService.getFiles(tile);
        assertTrue(Files.isDirectory(path));
    }

    @Test
    public void test_file_is_uploaded() throws IOException {
        assertTrue(adventureService.getFiles(tile).isEmpty());
        File file = new File("src/main/adventures/test/01/test_image.jpg");
        MultipartFile multipartFile = new MockMultipartFile("test_image.jpg", "test_image.jpg", "image/jpeg", Files.readAllBytes(file.toPath()));
        adventureService.uploadFile(multipartFile, tile);
        assertFalse(adventureService.getFiles(tile).isEmpty());
    }

    @Test
    public void test_directory_is_cleared() throws IOException {
        assertTrue(adventureService.getFiles(tile).isEmpty());
        File file = new File("src/main/adventures/test/01/test_image.jpg");
        MultipartFile multipartFile = new MockMultipartFile("test_image.jpg", "test_image.jpg", "image/jpeg", Files.readAllBytes(file.toPath()));
        adventureService.uploadFile(multipartFile, tile);
        assertFalse(adventureService.getFiles(tile).isEmpty());
        adventureService.clearDirectory(tile);
        assertTrue(adventureService.getFiles(tile).isEmpty());
    }

    @Test
    public void test_file_is_returned_from_get_specific_file() {
        Resource r = adventureService.getSpecificFile("test/01/test_image.jpg");
        assertNotNull(r);
    }

    @Test
    public void test_default_file_is_returned_from_false_file_name() {
        Resource r = adventureService.getSpecificFile("test/01/i_do_not_exist.jpg");
        assertNotNull(r);
    }
}