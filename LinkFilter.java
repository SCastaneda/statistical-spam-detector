public class LinkFilter implements Filter{

    @Override
    public String filter(String str) {
        
        StringBuffer result = new StringBuffer(str.length());
        Character current = ' ', next = ' ';

        boolean extract = false;
        StringBuffer aTag;

        for(int i = 0; i < str.length(); i++){
            current = str.charAt(i);
            if (i < str.length() - 1) {
                next = str.charAt(i+1);
            }else{
                next = ' '; 
            }

            if(extract) {
                aTag = new StringBuffer(128);

                // start of <a href=''> tag
                aTag.append(str.charAt(i));

                i++;
                while(i < str.length() && str.charAt(i) != '>') {
                    aTag.append(str.charAt(i));
                    i++;
                }
                i++;

                if(i < str.length()) {
                    aTag.append(str.charAt(i));
                }

                String link = extractHref(aTag.toString());
                result.append(link);
                extract = false;

            }

            if(!extract && current == '<' && (next == 'a' || next == 'A') ){
                extract = true;

                // go back to prev
                i = i-2;
            }

            if(i < str.length() && !extract) {
                result.append(str.charAt(i));
            }
        }
        
        return result.toString();
    }

    // expects an a tag as follows: <a xyz xyz="xyz" href="link" xyz xyz="xyz">
    // returns link
    public static String extractHref(String aTag) {
        // could not find link
        if(!aTag.contains("href=")) {
            return " ";
        } 
        
        String[] strArr = aTag.split("href=\"");

        //make sure it's only split into 2 pieces:
        if(strArr.length < 2) {
            return " ";
        }

        strArr = strArr[1].split("\"");
        String link = strArr[0];

        return link;
        
    }

}