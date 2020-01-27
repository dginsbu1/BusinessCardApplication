package App;

import javax.naming.Name;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
//Assumptions: Numbers of US numbers, emails appear by themselves (based on examples), names list constructed bases on US statistics.

public class BusinessCardParser{
    //used for setting up the set of names for name verification
    private static String sep = System.getProperty("file.separator");//either "/" or "\" for platform independence
    private static String wrkDir = System.getProperty("user.dir");
    private static String resourceDir = wrkDir + sep + "src" + sep + "main" + sep + "resources";
    private Set<String> nameSet;
    //NameParser nameParser;


    public BusinessCardParser(String namePath) throws FileNotFoundException {
        //nameParser = new NameParser();
        nameSet = new HashSet<>();
        //if the default option is chosen set the
        // nameSets to the predefined list
        if(namePath.equalsIgnoreCase("default")){
            setDefaultNameSet();
            return;
        }
        //assuming a filepath was given, upload all the names to nameSet from the file
        File nameFile = new File(namePath);
        Scanner sc = new Scanner(nameFile);
        while (sc.hasNextLine())
            nameSet.add(sc.nextLine());
    }

    //sets nameSet to predefined lists
    //male = 90%, female = 89%, lastName = 68% of population
    //https://names.mongabay.com/
    private void setDefaultNameSet() throws FileNotFoundException {
        File nameFile = new File(resourceDir+sep+"maleFirstNames.txt");
        Scanner sc = new Scanner(nameFile);
        while (sc.hasNextLine())
            nameSet.add(sc.nextLine());
        nameFile = new File(resourceDir+sep+"femaleFirstNames.txt");
        sc = new Scanner(nameFile);
        while (sc.hasNextLine())
            nameSet.add(sc.nextLine());
        nameFile = new File(resourceDir+sep+"lastNames.txt");
        sc = new Scanner(nameFile);
        while (sc.hasNextLine())
            nameSet.add(sc.nextLine());
    }

    //creates a new ContactInfo object, sets the name, email, and phoneNumber,
    //and returns the object
    public ContactInfo getContactInfo(String document){
        ContactInfo ci = new ContactInfo();
        //splits the the string into an array of individual lines
        String[] lines = document.split("\\r?\\n");
        //ci.setName(parseNameOpen(lines));
        ci.setName(parseName(lines));
        ci.setEmail(parseEmail(lines));
        ci.setPhoneNumber(parsePhoneNumber(lines));
        return ci;
    }

    //extracts the first name (if any) in lines
    //returns the first extracted name or null if none found
    public String parseName(String[] lines){
        for(String line: lines) {
            if (isName(line)) {
                return line;
            }
        }
        return null;
    }

    //returns true if the given String/line is a name
    //otherwise return false
    private boolean isName(String line) {
        //split the line into individual words
        String[] words = line.split("\\s+");
        for(String word : words) {
            //if any word is not a name then the line is not a name
            //middle initials such as Bob E Smith or Bob E. Smith are valid names
            if (!(nameSet.contains(word.toUpperCase()) || word.matches("[A-Z][.]?"))) {
                return false;
            }
        }
        //if all the words are names then the line is a name
        return true;
    }


    //assumes email appears by itself on a line based on the examples
    //returns the first extracted email or null if none found
    public String parseEmail(String[] lines){
        String validEmail = null;
        //https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        for(String line: lines){
            //turn it to lower case
            line = line.toLowerCase();
            if(line.matches(emailRegex)){
                return line;
            }
        }
        return null;
    }

    //extracts the phone number in lines
    //returns the first extracted email or null if none found
    //examples of valid numbers: (410)555-1234, Phone: 410-555-1234, Tel: +1 (703) 555-1259
    public String parsePhoneNumber(String[] lines){
        for(String line: lines){
            //check line to see if it contains the phone number
            if(!isPhoneNumberLine(line)){
                continue;
            }
            //if this line contains the phone number,
            //remove all non-numeric characters
            String numbers = line.replaceAll("[^0-9]", "");
            //simple check to make sure the number is between 10 and 11 digits (standard US number)
            if(numbers.length() < 10 || numbers.length() > 11){
                continue;
            }
            return numbers;
        }
        return null;
    }

    //checks if the line contains a phone number and it is not a fax number
    private boolean isPhoneNumberLine(String line){
        //Regex based on https://stackoverflow.com/questions/8634139/phone-validation-regex
        String phoneRegEx = ".*\\(?([0-9]{3})\\)?[-./ ]?([0-9]{3})[-./ ]?([0-9]{4})";
        //verify the line matched and the line is not a fax number
        if(line.matches(phoneRegEx) && !line.toLowerCase().contains("f")) {
            return true;
        }
        return false;
    }

    //extracts the name (if any) in lines and sets the name of ci to that name
    //returns the first extracted name
    /*
    public String parseNameOpen(String[] lines){
        return nameParser.parseName(lines);
    }
    */
}
