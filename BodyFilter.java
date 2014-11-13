
public class BodyFilter implements Filter {
	@Override
	public String filter(String str) {
		
		int si = 0;
		
		while (str.charAt(si) != 10 || str.charAt(si+1) != 10){
			si++;
		}
		si += 2;
		
		return str.substring(si, str.length());
	}
}
