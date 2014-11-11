import java.util.HashMap;

public class TokenCounter {
	public static void count(HashMap<String,Integer> tokenFreq, String str){
		String[] tokens = str.split("\\s");
		
		for(String s : tokens){
			if(s.length()>0){
				s = s.toLowerCase();
				if(tokenFreq.containsKey(s)){
					int freq = tokenFreq.get(s);
					freq++;
					tokenFreq.put(s, freq);
				}else{
					tokenFreq.put(s, 1);
				}
			}
		}
	}
}
