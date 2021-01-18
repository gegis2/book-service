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
     * @return status code
     */
    public static int addBook(Book book) {
        Book temp = findBook(book.getBarCode());
        if (temp != null) {
            return 403;
        } else {
            try (FileWriter mFileWriter = new FileWriter(path, true);
                    CSVWriter writer = new CSVWriter(mFileWriter, ',', CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.NO_ESCAPE_CHARACTER, System.getProperty("line.separator"))) {
                writer.writeNext(book.toCsvStringArray());
                return 200;
            } catch (IOException ex) {
                ex.printStackTrace();
                return 406;
            }
        }
    }

    public static int updateBook(String barCode, String collumn, String data) {
        Book book = findBook(barCode);
        System.out.println(book.toJsonString());
        if (book != null) {
            if (collumn.toLowerCase().equals("name"))
                book.setName(data);
            else if (collumn.toLowerCase().equals("author")) {
                book.setAuthor(data);
            } else if (collumn.toLowerCase().equals("barcode")) {
                book.setBarCode(data);
                System.out.println(book.toJsonString());
            } else if (collumn.toLowerCase().equals("price"))
                book.setPrice(Double.parseDouble(data));
            else if (collumn.toLowerCase().equals("quantity"))
                book.setQuantity(Integer.parseInt(data));
            else if (collumn.toLowerCase().equals("releaseyear"))
                book.setReleaseYear(Integer.parseInt(data));
            else if (collumn.toLowerCase().equals("scienceindex"))
                book.setScienceIndex(Integer.parseInt(data));
            else
                return 400;
            if (rewriteLine(findBookIndex(barCode), book))
                return 200;
            else
                return 400;
        } else
            return 404;
    }

    /**
     * replaces a line in the csv file with the provided string
     * 
     * @param lineNumber number of the line to be replaced
     * @param data       a book to replace with
     * @return false if failed true if succesfull
     */
    public static Boolean rewriteLine(int lineNumber, Book data) {
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
                            Double.parseDouble(lineInArray[3]), Integer.parseInt(lineInArray[4]),
                            Integer.parseInt(lineInArray[5]), Integer.parseInt(lineInArray[6]));
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
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] lineInArray;
            int index = 0;
            while ((lineInArray = reader.readNext()) != null) {
                if (lineInArray[2].equals(barCode)) {
                    return index;
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * if book exists, then return its total price
     * 
     * @param barCode
     * @return
     */
    public static double totalPrice(String barCode) {
        Book book = findBook(barCode);
        if (book != null) {
            Double total = book.totalPrice();
            return total;
        }
        return 0.0;
    }
}
