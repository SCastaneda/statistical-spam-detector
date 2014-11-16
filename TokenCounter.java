import java.util.HashMap;

public class TokenCounter {
	
	public static HashMap<String,Integer> count( String subject, String body){

		HashMap<String,Integer> tokenFreq = new HashMap();
		String[] stokens = subject.split("\\s");
		String[] btokens = body.split("\\s");
		
		for(String s : stokens){
			if(s.length()>1){
				s = "subject*" + s;
				s = s.toLowerCase();
				if(tokenFreq.containsKey(s)){
					tokenFreq.put(s, tokenFreq.get(s)+1);
				} else {
					tokenFreq.put(s,1);
				}
			}
		}

		for(String s : btokens){
			if(s.length()>1){
				s = s.toLowerCase();
				if(tokenFreq.containsKey(s)){
					tokenFreq.put(s, tokenFreq.get(s)+1);
				} else {
					tokenFreq.put(s,1);
				}
			}
		}

		return tokenFreq;
	}
}
