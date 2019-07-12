package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriterReader {

    public static void me(){
        String basePath = new File("").getAbsolutePath();
        System.out.println(basePath);
    }

    public static String readFile(String fileName) throws IOException {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("File was not located");
            e.printStackTrace();
        }
        return data;
    }

    public static void writeToFile(String fileName, String writtable) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        writer.append(writtable);

        writer.close();
    }

    public static void appendToFile(String fileName, String writtable) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(writtable);

        writer.close();
    }
}
