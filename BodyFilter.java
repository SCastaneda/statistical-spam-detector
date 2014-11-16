
public class BodyFilter implements Filter {
	@Override
	public String filter(String str) {
		String marker = "::body::";
		
		int sindex = str.indexOf(marker);
		if(sindex == -1) System.err.println("Body not found in email");
		
		sindex++;
		return str.substring(sindex, str.length());
	}
}
