package book_services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;

public class iostream {
    final static String path = new File(
            "book-service\\com.books.services\\src\\main\\java\\book_services\\Data\\books.csv").getAbsolutePath();

    public static boolean addBook(Book book) {
        try (FileWriter mFileWriter = new FileWriter(path, true);
                CSVWriter writer = new CSVWriter(mFileWriter, ',', CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.NO_ESCAPE_CHARACTER, System.getProperty("line.separator"))) {
            writer.writeNext(book.toCsvStringArray());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void updateBook(int lineNumber, String data) {
        try {
            Path tempPath = Paths.get(path);
            List<String> lines = Files.readAllLines(tempPath, StandardCharsets.UTF_8);
            lines.set(lineNumber, data);
            Files.write(tempPath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Book findBook(String barCode) {
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                if (lineInArray[2].equals(barCode)) {
                    Book book = new Book(lineInArray[0], lineInArray[1], lineInArray[2],
                            Double.parseDouble(lineInArray[3]), Integer.parseInt(lineInArray[4]));
                    return book;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int findBookIndex(String barCode) {
        return -1;
    }
}
