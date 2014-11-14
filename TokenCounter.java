import java.util.HashMap;

public class TokenCounter {
	
	public static HashMap<String,Integer> count( String subject, String body){

		HashMap<String,Integer> tokenFreq = new HashMap();
		String[] tokens = str.split("\\s");
		
		for(String s : subject){
			if(s.length()>0){
				s = "subject*" + s;
				tokenFreq.put(s, tokenFreq.get(s)+1);
			}
		}

		for(String s : body){
			if(s.length()>0){
				tokenFreq.put(s, tokenFreq.get(s)+1);
			}
		}

		return tokenFreq;
	}
}
