
public class BodyFilter implements Filter {
	@Override
	public String filter(String str) {
		String marker = "::Body::\n"
		
		int sindex = str.indexOf(marker);
		
		return str.substring(sindex, str.length());
	}
}
