
public class StyleFilter implements Filter{
	
	private int indexOf(String str, String tag, int i){
	
		String ltag = tag.toLowerCase();
		String htag = tag.toUpperCase();
		
		int indexA = str.indexOf(ltag,i);
		int indexB = str.indexOf(htag,i);
		
		if(indexA == -1) return indexB;
		if(indexB == -1) return indexA;
		
		return Math.min(indexA, indexB);
	}

	@Override
	public String filter(String str) {
		
		StringBuffer result = new StringBuffer("");
		
		int i = 0;
		String styleTag = "<style>";
		String styleTagClose = "</style>";
		
		while (i >= 0 && i < str.length()){
			
			// Look for opening tag
			int begin = indexOf(str,styleTag,i);
			
			if (begin == -1){
				// No more style tags. copy all
				result.append(str.substring(i, str.length()));
				i = str.length();
			}else{
				//Append anything before the opening tag
				result.append(str.substring(i, begin) + " ");
				
				//Look for closing tag
				int end = indexOf(str,styleTagClose, begin);
				if(end == -1){
					// No closing tag. nothing else to do.
					i = str.length();
				}else{
					// move index after closing tag
					i = end+styleTagClose.length();
				}
			}
		}
		
		
		return result.toString();
	}

}
