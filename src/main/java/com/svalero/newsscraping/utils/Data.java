package com.svalero.newsscraping.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static com.svalero.newsscraping.utils.Constants.LOG_PATH;
import static com.svalero.newsscraping.utils.Constants.URLS_PATH;

public class Data {
    private final Logger logger = LoggerFactory.getLogger(Data.class);
    public ArrayList<String> read(String path) {
        ArrayList<String> toReturn = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!(line.startsWith("http://") || line.startsWith("https://")) && path.equals(URLS_PATH)) {
                    Path logPath = Paths.get(LOG_PATH);
                    if (Files.notExists(logPath)) {
                        try {
                            Files.createFile(logPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_PATH, true))) {
                        writer.write("Invalid url: " + line);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    toReturn.add(line);
                }
            }
            return toReturn;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
