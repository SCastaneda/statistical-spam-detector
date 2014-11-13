
public class HtmlFilter implements Filter{

	@Override
	public String filter(String str) {
		
		String result = "";
		//String[] tokens = str.split("<.*>");
		boolean ignore = false;
		for(int i = 0;i < str.length(); i++){
			if(str.charAt(i) == '<'){
				ignore = true;
			}else if(str.charAt(i) == '>'){
				ignore = false;
				i++;
				if(str.charAt(i) == '<'){
					ignore = true;
				}
			}
			
			if(!ignore && i < str.length()){
				result += str.charAt(i);
			}
		}
		
		return result;
	}

}
