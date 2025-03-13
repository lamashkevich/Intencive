package ru.aston.lamashkevich_ev.task6;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TextFileUtils {

    //  1. Написать метод, который читает текстовый файл и возвращает его в виде списка строк.
    public List<String> readFileAndReturnStringList(File file) {
        List<String> strings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException("File reading error: " + file.getName(), e);
        }
    }

    //  2. Написать метод, который записывает в файл строку, переданную параметром.
    public void writeStringToFile(File file, String s) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() != 0) {
                writer.newLine();
            }
            writer.write(s);
        } catch (IOException e) {
            throw new RuntimeException("File writing error: " + file.getName(), e);
        }
    }

    //  3. Используя решение 1 и 2, напишите метод, который склеивает два текстовых файла один.
    public File mergeFiles(File f1, File f2) {
        String fileName = LocalDate.now() + "-merged.txt";
        File mergedFile = new File(fileName);

        List<String> list1 = readFileAndReturnStringList(f1);
        List<String> list2 = readFileAndReturnStringList(f2);

        Stream.concat(list1.stream(), list2.stream())
                .forEach(s -> writeStringToFile(mergedFile, s));

        return mergedFile;
    }

    //  4. Написать метод, который заменяет в файле все кроме букв и цифр на знак ‘$’
    public File replaceAllExceptLetterAndDigit(File file, char character) {
        String fileName = LocalDate.now() + "-replaced.txt";
        File replacedFile = new File(fileName);

        try (FileWriter writer = new FileWriter(replacedFile);
             FileReader reader = new FileReader(file)) {
            int currentChar;
            while ((currentChar = reader.read()) != -1) {
                if (Character.isLetterOrDigit(currentChar) || currentChar == '\n') {
                    writer.write(currentChar);
                } else {
                    writer.write(character);
                }
            }
            return replacedFile;
        } catch (IOException e) {
            throw new RuntimeException("Error replacing char in file: " + file.getName(), e);
        }
    }

}
