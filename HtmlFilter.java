
public class HtmlFilter implements Filter{

	@Override
	public String filter(String str) {
		
		StringBuffer result = new StringBuffer("");
		//String[] tokens = str.split("<.*>");
		boolean ignore = false;
		for(int i = 0;i < str.length(); i++){
			if(str.charAt(i) == '<'){
				ignore = true;
			}else if(str.charAt(i) == '>'){
				ignore = false;
				continue;
				//i++;
				//if(str.charAt(i) == '<'){
				//	ignore = true;
				//}
			}
			
			if(!ignore && i < str.length()){
				result.append(str.charAt(i));
			}
		}
		
		return result.toString();
	}

}
