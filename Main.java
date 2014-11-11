import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		HashMap<String,Integer> tokenFreq = new HashMap<String,Integer>();
		String str = "";
		// Open file and read all into str
		try{
			//str = new String(Files.readAllBytes(Paths.get("C:/workspace-java/SPAM/2000/03/952012137.23264.txt")));
			str = new String(Files.readAllBytes(Paths.get("test.html")));
		}catch(IOException e){
			System.err.println(e.getMessage());
		}
		
		/*
		// Parse the file
		SimpleParser parser = new SimpleParser();
		str = parser.filter(str);
		
		// Count tokens and add count into tokenFreq
		TokenCounter.count(tokenFreq, str);
		
		// Print results
		for(String s : tokenFreq.keySet()){
			System.out.println(s + " - " + tokenFreq.get(s));
		}
		*/
		
		String html = "<html> lolo </html>";
		HtmlFilter hf = new HtmlFilter();
		html = hf.filter(str);
		System.out.println(html);
	}

}
