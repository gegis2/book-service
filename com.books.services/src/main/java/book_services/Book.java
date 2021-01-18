package book_services;

public class Book {
    private String name;
    private String barCode;
    private String author;
    private double price;
    private int quantity;

    public Book() {
        name = "";
        barCode = "";
        author = "";
        price = 0.0;
        quantity = 0;
    }

    public Book(String name, String barCode, String author, double price, int quantity) {
        this.name = name;
        this.barCode = barCode;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * converts object to json format
     * 
     * @return string fit for json
     */
    public String toJsonString() {
        return "{ \"name\": \"" + name + " \",\"author\": \"" + author + " \",\"barCode\": \"" + barCode
                + "\",\"price\": " + price + " , \"quantity\": " + quantity + " } ";
    }

    /**
     * converts object to string array
     * 
     * @return array of strings in order of name author barCode price quantity
     */
    public String[] toCsvStringArray() {
        String[] csvLine = { name, author, barCode, price + "", quantity + "" };
        return csvLine;
    }

    /**
     * converts object to csv string with ',' seperator
     * 
     * @return csv string in order of name author barCode price quantity
     */
    public String toCsvStringLine() {
        String csvLine = name + "," + author + "," + barCode + "," + price + "," + quantity;
        return csvLine;
    }
}
