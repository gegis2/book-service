package book_services;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

    private String name;
    private String barCode;
    private String author;
    private double price;
    private int quantity;

    public Book() {
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

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return this.barCode;
    }

    @XmlAttribute
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getAuthor() {
        return this.author;
    }

    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return this.price;
    }

    @XmlElement
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    @XmlElement
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String jsonString() {
        return "{ \"name\": \"" + name + " \",\"author\": \"" + author + " \",\"bar code\": \"" + barCode
                + "\",\"price\": " + price + " , \"quantity\": " + quantity + " } ";
    }
}
