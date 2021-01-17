package book_services;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;
import book_services.Book;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        get("/put", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "barcode", "author", 5.5, 5);
                return book.jsonString();
            }
        });
    }
}
