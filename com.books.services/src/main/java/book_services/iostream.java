package book_services;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class iostream {
    final static String basePath = new File("book-service\\com.books.services\\src\\main\\java\\book_services")
            .getAbsolutePath();

    public static void appendData() {
        Book book = new Book("book", "barcode", "author", 5.5, 5);
        try (FileWriter mFileWriter = new FileWriter(basePath + "\\Data\\books.csv", true);
                CSVWriter writer = new CSVWriter(mFileWriter, ',', CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.NO_ESCAPE_CHARACTER, System.getProperty("line.separator"))) {
            writer.writeNext(book.csvString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
