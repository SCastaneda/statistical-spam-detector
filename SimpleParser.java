
public class SimpleParser {

    public String[] filter(String str) {
        
        SubjectFilter sf  = new SubjectFilter();
        BodyFilter    bf  = new BodyFilter();

        StyleFilter   stf = new StyleFilter();
        LinkFilter    lf  = new LinkFilter();
        HtmlFilter    hf  = new HtmlFilter();
        CharFilter    cf  = new CharFilter();
        
        String subjectStr = sf.filter(str);
        subjectStr = cf.filter(subjectStr);
        
        String bodyStr = bf.filter(str);

        bodyStr = stf.filter(bodyStr);
        bodyStr = lf.filter(bodyStr);
        bodyStr = hf.filter(bodyStr);
        bodyStr = cf.filter(bodyStr);

        String[] res = {subjectStr, bodyStr};
        
        return res;
    }
    
}
