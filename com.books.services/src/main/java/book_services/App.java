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

        get("/authenticate", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "test";
            }
        });
    }
}
