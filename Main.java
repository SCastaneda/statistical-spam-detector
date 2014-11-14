import java.io.*;
import java.util.*;

public class Main {

    String spamFile     = "spam.txt";
    String nonSpamFile  = "nonspam.txt";
    String trainingFile = "training.txt";

    public static void main(String[] args) {
        
        HashMap<String, Pair> tokenFreq = new HashMap();

        //open spam file, go through each email
        try {
            BufferedReader spamBr = new BufferedReader(new FileReader(spamFile));
            // go through entire File, and update the tokenFreq
            // set spam flag to true
            totalFreq = updateByFile(totalFreq, spamBr, true);

            BufferedReader nonSpamBr = new BufferedReader(new FileReader(nonSpamFile));
            // go through entire File, and update the tokenFreq
            // set spam flag to false
            totalFreq = updateByFile(totalFreq, nonSpamBr, false);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            spamBr.close();
            nonSpamBr.close();
        }

        // print tokenFreq to File
        // need to get total spam/non-spam emails count and print that to the file as well
        

    }

    public static String getNextEmail(BufferedReader br) {
        StringBuffer email = new StringBuffer(1024);
        String nextLine = br.readLine();

        while( nextLine != null && !nextLine.contains("::New Email::") ) {
            email.append(nextLine);
            nextLine = br.readLine();
        }

        return email.toString();

    }

    public static HashMap<String, Pair> addToTotalfreq(HashMap<String, Pair> totalFreq, HashMap<String, Integer> thisFreq, boolean spam) {
        for(String key : thisFreq.keySet()) {

            // found the current token! add to it
            if(totalFreq.containsKey(key)) {
                if(spam) {
                    totalFreq.get(key).setSpamCount( totalFreq.get(key).getSpamCount() + thisFreq.get(key) );
                } else {
                    totalFreq.get(key).setNonSpamCount( totalFreq.get(key).getNonSpamCount() + thisFreq.get(key) );
                }
            } else {
                Pair pair;
                if(spam) {
                    pair = new Pair(thisFreq.get(key), 0);

                } else {
                    pair = new Pair(0, thisFreq.get(key));
                }

                totalFreq.set(key, pair);
            }
        }

        return totalFreq;
    }

    public static HashMap<String, Pair> updateByFile(HashMap<String, Pair> totalFreq, BufferedReader br, boolean spam) {
        // the very first line should be ::New Email::
        String firstLine = br.readLine();
        if( !firstLine.contains("::New Email::") ) {
            System.out.printf("\n\nincorrect format: file's first line should be ::New Email::");
            System.exit(0);
        }

        // get next email
        String email = getNextEmail(br);
        while (email != null) {
            // run email through filters
            SimpleParser parser = new SimpleParser();
            String[] emailArr = parser.filter(email);

            // Count tokens and add count into tokenFreq
            totalFreq = addToTotalfreq(totalFreq, TokenCounter.count(emailArr[0], emailArr[1]), spam);  

            email = getNextEmail(br);
        }

        return totalFreq;
    }

}

