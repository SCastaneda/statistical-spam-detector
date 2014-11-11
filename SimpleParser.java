
public class SimpleParser implements Filter{

	@Override
	public String filter(String str) {
		
		SubjectFilter sf = new SubjectFilter();
		BodyFilter bf = new BodyFilter();
		CharFilter cf = new CharFilter();
		
		String subjectStr = sf.filter(str);
		subjectStr = cf.filter(subjectStr);
		
		String bodyStr = bf.filter(str);
		bodyStr = cf.filter(bodyStr);
		
		return subjectStr + " " + bodyStr;
	}
	
}
