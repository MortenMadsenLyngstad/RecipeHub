package file;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileUtilTest {
    private Path filePath = Path.of("test.txt");
    private FileUtil fileUtil = new FileUtil();

    private void deleteFile(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Test createFile")
    public void testCreateFile() {
        fileUtil.createFile(filePath);
        Assertions.assertTrue(Files.exists(filePath));
        deleteFile(filePath);
    }

    @Test
    @DisplayName("Test writeFile and readFile")
    public void testWriteAndReadFile() {
        fileUtil.createFile(filePath);
        fileUtil.writeFile(filePath, "test");
        Assertions.assertEquals("test", fileUtil.readFile(filePath, "", String.class));
        deleteFile(filePath);
    }

    @Test
    @DisplayName("Test Error writing to file")
    public void testErrorWritingToFile() {
        deleteFile(filePath);
        fileUtil.writeFile(filePath, "test");
        Assertions.assertDoesNotThrow(() -> fileUtil.writeFile(filePath, "test"));
        deleteFile(filePath);
    }
}