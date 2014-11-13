
public class SubjectFilter implements Filter{

	@Override
	public String filter(String str) {
		String marker = "Subject: ";
		
		int sindex = str.indexOf(marker);
		sindex += marker.length();
		
		// Search for newline
		int endindex = sindex;
		while(str.charAt(endindex)!= 10){
			endindex++;
		}
		
		return str.substring(sindex, endindex);
	}

}
