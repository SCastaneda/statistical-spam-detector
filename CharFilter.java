
public class CharFilter implements Filter{

	@Override
	public String filter(String str) {
		
		//StringBuffer result = new StringBuffer("");
		
		//String[] tokens = bodyStr.split("[^a-zA-Z\\d]");
		//String[] tokens = bodyStr.split("\\s");
		//str = str.replaceAll("[^\\p{Alnum}]", " ");
		// for(String s : tokens){
		// 	if(s.length() > 0){
		// 		result.append( s + " " );
		// 	}
		// }

		// for(int i = 0; i < str.length(); i++){
		// 	if(str.charAt(i) ){
		// 		result.append(str.charAt(i));
		// 	}else{
		// 		result.append(' ');
		// 	}
		// }
		
		return str.replaceAll("[^\\p{Alnum}]", " ");
	}

}
