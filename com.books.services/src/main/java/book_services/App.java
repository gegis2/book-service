package book_services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    final static String[] patterns = { "name:([\\w]*),", "author:([\\w]*),", "barcode:([\\w]*),",
            "price:([\\d]*?.?[\\d]*),", "quantity:([\\w]*)" };

    public static void main(String[] args) {
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Methods", "POST");
        });

        get("/retrieve", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String body = request.body();
                System.out.println(body);
                Book book = new Book("book", "author", "barcode", 5.5, 5);
                return book.toJsonString();
            }
        });

        post("/put", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book;
                List<String> dataParts = new ArrayList<String>();
                String body = request.body();
                for (String i : patterns) {
                    Pattern p = Pattern.compile(i, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(body);
                    if (m.find()) {
                        dataParts.add(m.group(1));
                    } else {
                        return "{\"status code\": 400}";
                    }
                }
                book = new Book(dataParts.get(0), dataParts.get(1), dataParts.get(2),
                        Double.parseDouble(dataParts.get(3)), Integer.parseInt(dataParts.get(4)));
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
