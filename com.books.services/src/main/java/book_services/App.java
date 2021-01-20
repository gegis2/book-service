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
    final static String[] putPatterns = { "name:([\\w]*)", "author:([\\w]*)", "barcode:([\\w]*)",
            "price:([\\d]*.?[\\d]*)", "quantity:([\\d]*)[ ]?[,}]" };
    final static String putSciencePattern = "scienceindex:([0-9]|10)[,}]";
    final static String putYearPattern = "releaseyear:(\\d*)";
    final static String barcodePattern = "([\\w]*)";
    final static String scienceIndexPattern = "([0-9]|10)";
    final static String[] updatePatterns = { "barcode:([\\w]*)", "collumn:([\\w]*)", "data:([\\w]*.?[\\d]*)" };

    public static void main(String[] args) {
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Methods", "POST");
        });

        get("/retrieve", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String barCode = request.queryParams("barcode");
                Pattern p = Pattern.compile(barcodePattern);
                Matcher m = p.matcher(barCode);
                if (m.find()) {
                    Book book = iostream.findBook(barCode);
                    if (book != null) {

                        System.out.println("sending book data " + book.toCsvStringLine());
                        return book.toJsonString();
                    } else
                        return "{\"status code\": 404}";
                } else
                    return "{\"status code\": 400}";
            }
        });

        post("/put", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book;
                List<String> dataParts = new ArrayList<String>();
                String body = request.body();
                boolean fullBook = false;
                for (String i : putPatterns) {
                    Pattern p = Pattern.compile(i, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(body);
                    if (m.find()) {
                        dataParts.add(m.group(1));
                    } else
                        return "{\"status code\": 400}";
                }
                Pattern p = Pattern.compile(putSciencePattern, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(body);
                if (m.find()) {
                    fullBook = true;
                    dataParts.add(m.group(1));
                } else
                    dataParts.add("0");

                p = Pattern.compile(putYearPattern, Pattern.CASE_INSENSITIVE);
                m = p.matcher(body);
                if (m.find()) {
                    fullBook = true;
                    dataParts.add(m.group(1));
                } else
                    dataParts.add("2020");
                if (fullBook)
                    book = new Book(dataParts.get(0), dataParts.get(1), dataParts.get(2),
                            Double.parseDouble(dataParts.get(3)), Integer.parseInt(dataParts.get(4)),
                            Integer.parseInt(dataParts.get(5)), Integer.parseInt(dataParts.get(6)));
                else
                    book = new Book(dataParts.get(0), dataParts.get(1), dataParts.get(2),
                            Double.parseDouble(dataParts.get(3)), Integer.parseInt(dataParts.get(4)));
                System.out.println("received book " + book.toCsvStringLine());
                return "{\"status code\": " + iostream.addBook(book) + "}";
            }
        });

        post("/update", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String body = request.body();
                List<String> dataParts = new ArrayList<String>();
                for (String i : updatePatterns) {
                    Pattern p = Pattern.compile(i, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(body);
                    if (m.find()) {
                        dataParts.add(m.group(1));
                    } else
                        return "{\"status code\": 400}";
                }
                if (dataParts.get(1).toLowerCase().equals("price")) {
                    try {
                        Double.parseDouble(dataParts.get(2));
                    } catch (NumberFormatException e) {
                        return "{\"status code\": 403}";
                    }
                }
                if (dataParts.get(1).toLowerCase().equals("quantity")
                        || dataParts.get(1).toLowerCase().equals("releaseyear")) {
                    try {
                        Double.parseDouble(dataParts.get(2));
                    } catch (NumberFormatException e) {
                        return "{\"status code\": 403}";
                    }
                }
                if (dataParts.get(1).toLowerCase().equals("scienceindex")) {
                    Pattern p = Pattern.compile(scienceIndexPattern, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(dataParts.get(2));
                    if (!m.find())
                        return "{\"status code\": 403}";
                }

                System.out.println("received update request for book " + dataParts.get(0));
                return "{\"status code\": " + iostream.updateBook(dataParts.get(0), dataParts.get(1), dataParts.get(2))
                        + "}";
            }
        });

        get("/totalPrice", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String barCode = request.queryParams("barcode");
                Pattern p = Pattern.compile(barcodePattern);
                Matcher m = p.matcher(barCode);
                if (m.find()) {
                    Double totalPrice = iostream.totalPrice(barCode);
                    if (totalPrice != 0.0) {
                        System.out.println("received total price request for book " + barCode);
                        return "{'total_price':" + totalPrice + ",'barcode':'" + barCode + "''}";
                    } else
                        return "{\"status code\": 404}";
                } else
                    return "{\"status code\": 400}";
            }
        });
    }
}
