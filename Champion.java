import java.util.*;

/**
 * This class keeps the words with highest and lowest probability of being spam.
 * For this, it keeps two sets of champions: high champions and low champions.
 * Each time a new "contestant" (a pair of word and value) appears it is 
 * compared with the current champions and gets stored if value is high or
 * low enough.
 */
public class Champion {

	/**
	 * Default constructor. Only one champion is stored.
	 */
	public Champion(){
		n = 1;
		lowVal = new ArrayList<Double>();
		lowWord = new ArrayList<String>();
		highVal = new ArrayList<Double>();
		highWord = new ArrayList<String>();
	}

	/**
	 * Stores maxChampions.
	 */
	public Champion(int maxChampions){
		n = maxChampions;
		lowVal = new ArrayList<Double>();
		lowWord = new ArrayList<String>();
		highVal = new ArrayList<Double>();
		highWord = new ArrayList<String>();
	}
	
	/**
	 * Test a new word and value against the current champions.
	 * If the value is high or low enough, the word is stored
	 * among the appropriate champions (high or low).
	 */
	public void testContestant(String word, double value){
		// Test for high value
		if(highVal.isEmpty()){
			// No competition. Just add the contestant.
			highVal.add(value);
			highWord.add(word);
		}else{
			// Test the contestant against the current champions.
			int pos = 0;
			while(pos < highVal.size() && value < highVal.get(pos)){
				pos++;
			}
			highVal.add(pos,value);
			highWord.add(pos,word);
			if(highVal.size() > n){
				// Full house. Somebody has to go. Let's kick the weakest one.
				highVal.remove(n);
				highWord.remove(n);
			}
		}
		
		// Test for low value
		if(lowVal.isEmpty()){
			// No competition. Just add the contestant.
			lowVal.add(value);
			lowWord.add(word);
		}else{
			// Test the contestant against the current champions.
			int pos = 0;
			while(pos < lowVal.size() && value > lowVal.get(pos)){
				pos++;
			}
			lowVal.add(pos,value);
			lowWord.add(pos,word);
			if(lowVal.size() > n){
				// Full house. Somebody has to go. Let's kick the weakest one.
				lowVal.remove(n);
				lowWord.remove(n);
			}
		}
	}
	
	public String getHighChampion(int pos){
		if(pos < highWord.size()){
			return highWord.get(pos);
		}
		return null;
	}
	
	public double getHighChampionValue(int pos){
		if(pos < highVal.size()){
			return highVal.get(pos);
		}
		return -1.0;
	}
	
	public String getLowChampion(int pos){
		if(pos < lowWord.size()){
			return lowWord.get(pos);
		}
		return null;
	}
	
	public double getLowChampionValue(int pos){
		if(pos < lowVal.size()){
			return lowVal.get(pos);
		}
		return -1.0;
	}
	
	public int highSize(){
		return highVal.size();
	}
	
	public int lowSize(){
		return lowVal.size();
	}

	/**
	 * Created just for testing class Champion.
	 */
	public static void main(String[] args) {
		Champion champ = new Champion(3);
		
		champ.testContestant("three", 3);
		champ.testContestant("one", 1);
		champ.testContestant("two", 2);
		champ.testContestant("six", 6);
		champ.testContestant("four", 4);
		champ.testContestant("five", 5);
		champ.testContestant("seven", 7);
		
		System.out.println("High Champions:");
		for(int i = 0; i < champ.highSize(); i++){
			System.out.println(champ.getHighChampion(i));
		}
		
		System.out.println("Low Champions:");
		for(int i = 0; i < champ.highSize(); i++){
			System.out.println(champ.getLowChampion(i));
		}
	}
	
	// Amount of champions in this container.
	private int n;

	// Arrays that contains the current champion words.
	ArrayList<Double> lowVal;
	ArrayList<String> lowWord;
	ArrayList<Double> highVal;
	ArrayList<String> highWord;
}