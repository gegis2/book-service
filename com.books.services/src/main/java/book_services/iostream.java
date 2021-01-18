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
import java.util.List;
import java.io.File;
import java.io.FileReader;

public class iostream {
    final static String path = new File(
            "book-service\\com.books.services\\src\\main\\java\\book_services\\Data\\books.csv").getAbsolutePath();

    /**
     * adds a book to the csv file
     * 
     * @param book
     * @return false if failed; true if succesfull
     */
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

    /**
     * replaces a line in the csv file with the provided string
     * 
     * @param lineNumber number of the line to be replaced
     * @param data       a book to replace with
     * @return false if failed true if succesfull
     */
    public static Boolean updateBook(int lineNumber, Book data) {
        try {
            Path tempPath = Paths.get(path);
            List<String> lines = Files.readAllLines(tempPath, StandardCharsets.UTF_8);
            lines.set(lineNumber, data.toCsvStringLine());
            Files.write(tempPath, lines, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * finds a book via its barcode
     * 
     * @param barCode
     * @return Book object, null if doesnt exist
     */
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

    /**
     * finds the index of the book line in csv file
     * 
     * @param barCode bar code string of the book
     * @return index if found -1 if doesnt exist
     */
    public static int findBookIndex(String barCode) {
        return -1;
    }
}
