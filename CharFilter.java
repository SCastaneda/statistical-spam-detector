
public class CharFilter implements Filter{

	@Override
	public String filter(String str) {
		
		String result = "";
		
		//String[] tokens = bodyStr.split("[^a-zA-Z\\d]");
		//String[] tokens = bodyStr.split("\\s");
		String[] tokens = str.split("[^\\p{Alnum}]");
		for(String s : tokens){
			if(s.length() > 0) result += (s + " ");
		}
		
		return result;
	}

}
