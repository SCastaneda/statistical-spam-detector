import java.util.*;

public class EmailParser {
    
    // this is the parser for a single email
    // return a hashMap with frequency of tokens
    public static HashMap<String, Integer> parseSingleEmail(String email) {
        HashMap<String, Integer> tokenFreq = new HashMap<String, Integer>();
        // increment frequency example:
        // according to some stackoverflow sources, 
        // put will check for null and create it if it doesn't exist
        // if not we can check manually
        // tokenFreq.put(key, map.get(key) + 1);

        return tokenFreq;
    }

    // take in a single file with many emails
    // split up emails and call parseSingleEmail
    // return the combined token frequency
    public static HashMap<String, Integer> parseEmailsAndSave(String fileName) {
        HashMap<String, Integer> tokenFreq = new HashMap<String, Integer>();


        return tokenFreq;
    }

    public static void saveHashMap(int spam, int nonspam, HashMap<String, Integer> tokenFreq){
        PrintWriter out = new PrintWriter(new FileWriter("hashMap.txt"))); 
        out.println(spam + " " + nonspam); 


        //Iterate over hashmap
        Iterator it = tokenFreq.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            //print token then frequency
            out.println(pairs.getKey() + " " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        out.flush(); 
        out.close();
        return;
    }

}
