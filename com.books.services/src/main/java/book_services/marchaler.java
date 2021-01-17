package book_services;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Marchaler {

    public static void append() {
        Book book = new Book("book", "barcode", "author", 5.5, 5);

        try {

            File file = new File(
                    "C:\\Users\\minda\\Desktop\\Books\\book-service\\com.books.services\\src\\main\\java\\book_services\\Data\\books.xml");
            if (file.exists()) {
                System.out.println("file exists");
            } else {

                System.out.println("file doesnt exist");
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(book, file);
            jaxbMarshaller.marshal(book, System.out);

        } catch (

        JAXBException e) {
            e.printStackTrace();
        }

    }

}
