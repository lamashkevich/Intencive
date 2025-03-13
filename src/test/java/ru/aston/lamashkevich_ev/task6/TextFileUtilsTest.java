package ru.aston.lamashkevich_ev.task6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextFileUtilsTest {

    private final TextFileUtils utils = new TextFileUtils();
    private final List<File> createdFiles = new ArrayList<>();

    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        testFile = Files.createTempFile("temp", ".txt").toFile();
        createdFiles.add(testFile);
    }

    @AfterEach
    public void deleteTempFiles() {
        for (File file: createdFiles) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    void testReadFileAndReturnStringsList() throws IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("String number 1\n");
            writer.write("String number 2\n");
        }

        var result = utils.readFileAndReturnStringList(testFile);

        var expected = List.of("String number 1", "String number 2");
        assertNotNull(result);
        assertEquals(expected, result);

    }

    @Test
    void testWriteStringToFile() throws IOException {
        var s = "Test string";

        utils.writeStringToFile(testFile, s);

        assertNotNull(s);
        assertEquals(s, Files.readString(testFile.toPath()));
    }

    @Test
    void testMergeFiles() throws IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("String number 1\n");
            writer.write("String number 2\n");
        }

        var result = utils.mergeFiles(testFile, testFile);
        createdFiles.add(result);

        var excepted = List.of(
                "String number 1",
                "String number 2",
                "String number 1",
                "String number 2"
        );
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(excepted, Files.readAllLines(result.toPath()));
    }

    @Test
    void testReplaceAllExceptLetterAndDigit() throws IOException {
        var text = """
                String number 1,
                string #2.
                """;
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(text);
        }

        var result = utils.replaceAllExceptLetterAndDigit(testFile, '$');
        createdFiles.add(result);

        var excepted = """
                String$number$1$
                string$$2$
                """;
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(excepted, Files.readString(result.toPath()));
    }

}