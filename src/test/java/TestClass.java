import App.BusinessCardParser;
import App.ContactInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BusinessCardParser parser = new BusinessCardParser("default");
        String document = "";
        String path = "example3.txt";
        document = new String(Files.readAllBytes(Paths.get(path)));
        ContactInfo ci = parser.getContactInfo(document);
        //display the results
        System.out.println(document);
        System.out.println("==>\n");
        ci.display();
        System.out.println("-------------------");

    }
}
