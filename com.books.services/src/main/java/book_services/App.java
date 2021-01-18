package book_services;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Methods", "POST");
        });

        get("/retrieve", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "barcode", "author", 5.5, 5);
                return book.toJsonString();
            }
        });

        post("/put", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "barcode", "author", 5.5, 5);
                if (iostream.addBook(book))
                    return "{\"status\": 200}";
                else
                    return "{\"status\": 406}";
            }
        });

        post("/update", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "barcode", "author", 5.5, 5);
                return book.toJsonString();
            }
        });

        get("/totalPrice", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "barcode", "author", 5.5, 5);
                return book.toJsonString();
            }
        });
    }
}
