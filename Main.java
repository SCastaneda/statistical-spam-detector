import java.io.*;
import java.util.*;

public class Main {

    static String spamFile     = "spam.txt";
    static String nonSpamFile  = "nonspam.txt";
    static String trainingFile = "training.txt";
    static String commonWords  = "common.txt";

    public static void main(String[] args) {
        
        HashMap<String, Pair> tokenFreq = new HashMap();

        BufferedReader spamBr = null;
        BufferedReader nonSpamBr = null;
        
        //open spam file, go through each email
        try {
            spamBr = new BufferedReader(new FileReader(spamFile));
            // go through entire File, and update the tokenFreq
            // set spam flag to true
            tokenFreq = updateByFile(tokenFreq, spamBr, true);

            nonSpamBr = new BufferedReader(new FileReader(nonSpamFile));
            // go through entire File, and update the tokenFreq
            // set spam flag to false
            tokenFreq = updateByFile(tokenFreq, nonSpamBr, false);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try { nonSpamBr.close();spamBr.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        BufferedReader commonBuffer = null;

        try {
            // remove common words
            commonBuffer = new BufferedReader(new FileReader(commonWords));
            String word = commonBuffer.readLine();
            while(word != null) {
                tokenFreq.remove(word);
                word = commonBuffer.readLine();
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }

        // print tokenFreq to File
        // total spam/non-spam emails count is in tokenFreq with key ::email count::
        writeTrainingFile(tokenFreq, trainingFile);

    }

    public static void writeTrainingFile(HashMap<String, Pair> tokenFreq, String trainingFile) {
        
        FileWriter fw = null;
        try {
            // first line of the file has the total email freq
            int spamCount = tokenFreq.get("::email count::").getSpamCount();
            int nonSpamCount = tokenFreq.get("::email count::").getNonSpamCount();

            fw = new FileWriter(trainingFile);
            
            // write first line
            fw.write(String.valueOf(spamCount) + " " + String.valueOf(nonSpamCount) + "\n");

            // remove this token to make sure it's not added as a token
            tokenFreq.remove("::email count::");

            // write tokens, their spam count, and non-spam count to the file
            for(String key : tokenFreq.keySet()) {
                fw.write(key + " " + tokenFreq.get(key).getSpamCount() + " " + tokenFreq.get(key).getNonSpamCount() + "\n");
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try { fw.close(); } catch(Exception e) { e.printStackTrace(); }
        }
    }

    public static String getNextEmail(BufferedReader br) throws Exception {
        StringBuffer email = new StringBuffer(1024);
        
        String nextLine = br.readLine();
        if(nextLine == null) {
            return nextLine;
        }


        if(nextLine != null && nextLine.contains("::new email::")) {
            nextLine = br.readLine();
        }

        while( nextLine != null && !nextLine.contains("::new email::") ) {
            email.append(nextLine + "\n");
            nextLine = br.readLine();
        }
          
        return email.toString();

    }

    public static HashMap<String, Pair> addToTotalfreq(HashMap<String, Pair> tokenFreq, HashMap<String, Integer> thisFreq, boolean spam) {
        for(String key : thisFreq.keySet()) {

            // found the current token! add to it
            if(tokenFreq.containsKey(key)) {
                if(spam) {
                    tokenFreq.get(key).setSpamCount( tokenFreq.get(key).getSpamCount() + thisFreq.get(key) );
                } else {
                    tokenFreq.get(key).setNonSpamCount( tokenFreq.get(key).getNonSpamCount() + thisFreq.get(key) );
                }
            } else {
                Pair pair;
                if(spam) {
                    pair = new Pair(thisFreq.get(key), 0);

                } else {
                    pair = new Pair(0, thisFreq.get(key));
                }

                tokenFreq.put(key, pair);
            }
        }

        return tokenFreq;
    }

    public static HashMap<String, Pair> updateByFile(HashMap<String, Pair> totalFreq, BufferedReader br, boolean spam) throws Exception {
        // the very first line should be ::New Email::
        String firstLine = br.readLine();
        if( !firstLine.contains("::new email::") ) {
            System.out.printf("incorrect format: file's first line should be ::New Email::\n");
            System.exit(0);
        }

        int count = 0;

        // get next email
        String email = getNextEmail(br);
        while (email != null) {
            // run email through filters
            SimpleParser parser = new SimpleParser();
            String[] emailArr = parser.filter(email);

            // Count tokens and add count into tokenFreq
            totalFreq = addToTotalfreq(totalFreq, TokenCounter.count(emailArr[0], emailArr[1]), spam);  

            email = getNextEmail(br);
            count++;
        }

        HashMap<String, Integer> emailCount = new HashMap();
        emailCount.put("::email count::", count);
        totalFreq = addToTotalfreq(totalFreq, emailCount, spam);

        return totalFreq;
    }

}

