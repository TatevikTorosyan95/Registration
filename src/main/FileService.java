package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileService {
    public static void createFolder(String path) {
        File f = new File(path);
        f.mkdirs();
    }

    public static void createFile(String path, String name) {
        File file = new File(path + File.separator + name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String path, String text) {
        try {
            Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] readLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path)).toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

}
