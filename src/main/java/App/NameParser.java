package App;
/*


import java.io.*;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.util.Span;
import opennlp.tools.util.InvalidFormatException;
*/


class NameParser {
    /*
    private static String sep = System.getProperty("file.separator");//either "/" or "\"
    private static String wrkDir = System.getProperty("user.dir");
    private static String resourceDir = wrkDir + sep + "src" + sep + "main" + sep + "resources";
    private InputStream inputStream = null;
    private TokenNameFinderModel model = null;
    private NameFinderME nameFinder;
    //private Tokenizer tokenizer;

    public NameParser() {
        //Loading the NER - Person model
        try {
            //tokenizer = new TokenizerME(new TokenizerModel(ClassLoader.getSystemResourceAsStream("en-token.bin")));
            inputStream = new FileInputStream(resourceDir + sep + "en-ner-person.bin");
            model = new TokenNameFinderModel(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Instantiating the NameFinder class
        nameFinder = new NameFinderME(model);
    }

    //return the line that is most likely to hold the name
    public String parseName(String[] lines) {
        //keep track of most likely line
        double highestPercent = 0;
        String name = null;
        for (String line : lines) {
            //lines with numbers can't be names
            if(line.matches(".*\\d.*")){
                continue;
            }
            //System.out.println(line);
            double currentPercent = 0;
            //break up the line by spaces
            String[] words = line.split("\\s+");
            //for(String word: words){System.out.print(word);}
            //System.out.println("");
            //check if each word is a name
            //Finding the names in the sentence
            Span nameSpans[] = nameFinder.find(words);
            //no possible names found
            if(nameSpans.length == 0){
                continue;
            }
            //System.out.println(nameSpans.length);
            //Set the currentPercent to the lowest of the percents
            for (Span span : nameSpans) {
                //System.out.println("The current Word is "+words[span.getStart()]+" with a probability of "+span.getProb());
                currentPercent+=span.getProb();
            }
            currentPercent/=nameSpans.length;
            //if the currentProbability is greater than the highest probability,
            //than this line most likely contains the name
            if(currentPercent > highestPercent){
                System.out.println("New name is "+line);
                name = line;
            }
        }
        return name;
    }


    //Loading the NER-person model
    public static void main(String args[]) throws Exception {
        //Loading the NER - Person model
        InputStream inputStream = new FileInputStream(resourceDir + sep + "en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
        //Instantiating the NameFinder class
        NameFinderME nameFinder = new NameFinderME(model);
        //Getting the sentence in the form of String array
        String[] sentence = new String[]{
                "Mike",
                "and",
                "Smith",
                "are",
                "good",
                "friends"
        };

        //Finding the names in the sentence
        Span nameSpans[] = nameFinder.find(sentence);

        //Printing the spans of the names in the sentence
        for (Span s : nameSpans) {
            //System.out.println(s.toString());
            System.out.println(sentence[s.getStart()]);
        }
    }
    */
}