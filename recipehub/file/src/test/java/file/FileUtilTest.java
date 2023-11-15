package file;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This is a test class for FileUtil.
 */
public class FileUtilTest {
    private Path filePath = Path.of("test.txt");

    /**
     * Helper method to delete testfiles.
     */
    private void deleteFile(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests if the file is created correctly.
     * 
     * @see FileUtil#createFile(Path)
     */
    @Test
    @DisplayName("Test createFile")
    public void testCreateFile() {
        FileUtil.createFile(filePath);
        Assertions.assertTrue(Files.exists(filePath));
        // Since the file already exists, this will test the rest of the if-statement
        FileUtil.createFile(filePath);
        deleteFile(filePath);
    }

    /**
     * Tests if the file is written to and read from correctly.
     * 
     * @see FileUtil#writeFile(Path, Object)
     * @see FileUtil#readFile(Path, Object, Class)
     */
    @Test
    @DisplayName("Test writeFile and readFile")
    public void testWriteAndReadFile() {
        FileUtil.createFile(filePath);
        FileUtil.writeFile(filePath, "test");
        Assertions.assertEquals("test", FileUtil.readFile(filePath, "", String.class));
        deleteFile(filePath);
    }
}