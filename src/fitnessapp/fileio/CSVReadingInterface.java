package fitnessapp.fileio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads a CSV and interprets its rows
 */
public interface CSVReadingInterface {
    public static final Logger logger = Logger.getLogger(CSVReadingInterface.class.getName());
    public static final String DELIMINTER = ",";

    /**
     * Converts the CSV rows into String arrays and then gathers the String arrays into a list containing them all
     * @param csvFile the path to the csv file
     * @return a list of csv rows seperated by column
     * @throws IOException
     */
    public static List<String[]> read(String csvFile) throws IOException {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Path.of(csvFile))) {
            String line = null;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if(lineNumber != 0) { // Skip the first line since that is usually the header
                    String[] temp = Arrays.stream(line.split(DELIMINTER)).map(String::strip).toArray(String[]::new);
                    list.add(temp);
                }
                lineNumber++;
            }
            logger.log(Level.INFO, "CSV " + csvFile + " interpreted successfully");
            return list;
        }
    }
}