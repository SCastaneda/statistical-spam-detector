import java.io.*;
import java.util.*;

public class Main {

    static String spamFile     = "spam.txt";
    static String nonSpamFile  = "nonspam.txt";
    static String trainingFile = "training.txt";
    static String commonWords  = "common.txt";

    static String STATE = "train"; // default state is to train

    public static void main(String[] args) {
        
        String[] words = parseArgs(args);

        if(STATE == "train") {
            runTrainingState();
        } else if(STATE == "test") {
            runTestingState();
        } else if(STATE == "words") {
            runWordsState(words);
        }

    }

    public static void runTestingState() {

    }

    public static void runWordsState(String[] words) {
        Classifier cl = new Classifier();
        for(int i = 0; i < words.length; i++) {
            System.out.println(words[i] + ": " + cl.getSpamProb(words[i]));
        }
    }

    public static void runTrainingState() {
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

    public static String[] parseArgs(String[] args) {

        String[] words = null;

        if(args[0].compareTo("train") == 0 ) {
            STATE = "train";
        } else if(args[0].compareTo("test") == 0 ) {
            STATE = "test";
        } else if(args[0].compareTo("words") == 0 ) {
            STATE = "words";

            words = new String[args.length -1];

            for(int i = 1; i < args.length; i++) {
                words[i-1] = args[i];
            }

        } else {
            printHelpAndExit();
        }

        return words;

    }

    public static void printHelpAndExit() {
        System.out.printf("Usage:\n");
        System.out.printf("\tjava Main [option] [args]\n");
        System.out.printf("\t\toptions\n");
        System.out.printf("\t\t\ttrain\twill run the training cycle and output to training.txt (default)\n");
        System.out.printf("\t\t\t\trequires a spam.txt and nonspam.txt file\n");
        System.out.printf("\t\t\ttest\twill run the test cycle. Requires a training.txt file\n");
        System.out.printf("\t\t\twords\twill take in additional [args], and return probability of them being spam\n");

        System.exit(0);

    }

}

