package book_services;

import java.time.LocalDate;

public class Book {
    private String name;
    private String barCode;
    private String author;
    private double price;
    private int quantity;
    private int scienceIndex;
    private int releaseYear;

    public Book() {
        name = "";
        barCode = "";
        author = "";
        price = 0.0;
        quantity = 0;
    }

    /**
     * regular book
     * 
     * @param name
     * @param barCode
     * @param author
     * @param price
     * @param quantity
     */
    public Book(String name, String barCode, String author, double price, int quantity) {
        this.name = name;
        this.barCode = barCode;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.scienceIndex = 0;
        this.releaseYear = 1901;
    }

    /**
     * science journal or antique books
     * 
     * @param name
     * @param barCode
     * @param author
     * @param price
     * @param quantity
     * @param scienceIndex 0 if not science journal
     * @param releaseYear  1901 if not antique book
     */
    public Book(String name, String barCode, String author, double price, int quantity, int scienceIndex,
            int releaseYear) {
        this.name = name;
        this.barCode = barCode;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.scienceIndex = scienceIndex;
        this.releaseYear = releaseYear;
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

    public int getScienceIndex() {
        return this.scienceIndex;
    }

    public void setScienceIndex(int scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * converts object to json format
     * 
     * @return string fit for json
     */
    public String toJsonString() {
        String line = "{ \"name\": \"" + name + " \",\"author\": \"" + author + " \",\"barCode\": \"" + barCode
                + "\",\"price\": " + price + " , \"quantity\": " + quantity + " , \"science Index\": " + scienceIndex
                + ", \"release year\": " + releaseYear + " } ";
        return line;
    }

    /**
     * converts object to string array
     * 
     * @return array of strings in order of name author barCode price quantity
     */
    public String[] toCsvStringArray() {
        String[] csvLine = { name, author, barCode, price + "", quantity + "", "" + scienceIndex, "" + releaseYear };
        return csvLine;
    }

    /**
     * converts object to csv string with ',' seperator
     * 
     * @return csv string in order of name author barCode price quantity
     */
    public String toCsvStringLine() {
        String csvLine = name + "," + author + "," + barCode + "," + price + "," + quantity + "," + scienceIndex + ","
                + releaseYear;
        return csvLine;
    }

    public Double totalPrice() {
        Double total = quantity * price;
        if (releaseYear < 1900) {
            LocalDate currentdate = LocalDate.now();
            int year = currentdate.getYear();
            total = (year - releaseYear) / 10 * total;
        }
        if (scienceIndex >= 1) {
            total = total * scienceIndex;
        }
        return total;
    }
}
