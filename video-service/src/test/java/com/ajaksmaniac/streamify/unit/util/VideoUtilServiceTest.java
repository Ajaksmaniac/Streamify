package com.ajaksmaniac.streamify.unit.util;

import com.ajaksmaniac.streamify.util.VideoUtilService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VideoUtilServiceTest {

    private static final String UPLOAD_DIR = "Files-Upload";
    private static final String FILE_ID = "123";
    private static final String FILE_NAME = "test.mp4";
    private static final String FILE_NAME_UPDATED = "updated.mp4";
    private static final Path UPLOAD_PATH = Paths.get(UPLOAD_DIR);

    private List<Path> createdFiles;

    @BeforeEach
    void setUp() {
        createdFiles = new ArrayList<>();
    }

    @AfterEach
    void tearDown() throws IOException {
        for (Path path : createdFiles) {
            Files.deleteIfExists(path);
        }
    }

    @Test
    void testSaveFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_NAME, "video/mp4", "test content".getBytes());
        VideoUtilService.saveFile(FILE_ID, FILE_NAME, multipartFile);

        Path expectedFilePath = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME);
        assertTrue(Files.exists(expectedFilePath));

        createdFiles.add(expectedFilePath);
    }

    @Test
    void testGetFileAsResource() throws IOException {
        Path testFile = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME);
        Files.createFile(testFile);
        createdFiles.add(testFile);

        VideoUtilService videoUtilService = new VideoUtilService();
        Resource resource = videoUtilService.getFileAsResource(FILE_ID);

        assertNotNull(resource);
        assertEquals(testFile.toUri(), resource.getURI());
    }

    @Test
    void testGetFileAsResource_NotFound() throws IOException {
        VideoUtilService videoUtilService = new VideoUtilService();
        Resource resource = videoUtilService.getFileAsResource(FILE_ID);

        assertNull(resource);
    }

    @Test
    void testDeleteFile() throws IOException {
        Path testFile = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME);
        Files.createFile(testFile);
        createdFiles.add(testFile);

        VideoUtilService.deleteFile(FILE_ID);

        assertFalse(Files.exists(testFile));
    }

    @Test
    void testUpdateVideo_WithNewFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_NAME, "video/mp4", "test content".getBytes());

        Path existingFile = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME);
        Files.createFile(existingFile);
        createdFiles.add(existingFile);

        VideoUtilService.updateVideo(FILE_ID, FILE_NAME_UPDATED, multipartFile);

        Path expectedFilePath = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME_UPDATED);
        assertFalse(Files.exists(existingFile));
        assertTrue(Files.exists(expectedFilePath));

        createdFiles.add(expectedFilePath);
    }

    @Test
    void testUpdateVideo_RenameFile() throws IOException {
        Path existingFile = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME);
        Files.createFile(existingFile);
        createdFiles.add(existingFile);

        VideoUtilService.updateVideo(FILE_ID, FILE_NAME_UPDATED);

        Path expectedFilePath = UPLOAD_PATH.resolve(FILE_ID + "-" + FILE_NAME_UPDATED);
        assertFalse(Files.exists(existingFile));
        assertTrue(Files.exists(expectedFilePath));

        createdFiles.add(expectedFilePath);
    }
}
