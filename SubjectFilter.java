
public class SubjectFilter implements Filter{

	@Override
	public String filter(String str) {
		String marker = "::subject::";

		int sindex = str.indexOf(marker);
		sindex += marker.length()+1;
		
		// Search for newline
		int endindex = sindex;
		while(str.charAt(endindex) != '\n'){
			endindex++;
		}
		
		return str.substring(sindex, endindex);
	}

}
