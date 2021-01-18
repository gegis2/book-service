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
                Book book = new Book("book", "author", "barcode", 5.5, 5);
                return book.toJsonString();
            }
        });

        post("/put", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = new Book("book", "author", "barcode22", 5.5, 5, 5, 1800);
                return "{\"status code\": " + iostream.addBook(book) + "}";
            }
        });

        post("/update", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "{\"status code\": " + iostream.updateBook("newbarcode", "barcode", "barcode22") + "}";
            }
        });

        get("/totalPrice", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Double total = iostream.totalPrice("barcode2");
                if (total == 0.0)
                    return "{\"status code\": 404}";
                else
                    return total;
            }
        });
    }
}
