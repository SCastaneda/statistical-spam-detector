import java.util.*;

/**
 * This class keeps the words with highest and lowest probability of being spam.
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
		cVals = new ArrayList<Double>();
		vals = new ArrayList<Double>();
		words = new ArrayList<String>();
	}

	/**
	 * Stores maxChampions.
	 */
	public Champion(int maxChampions){
		n = maxChampions;
		cVals = new ArrayList<Double>();
		vals = new ArrayList<Double>();
		words = new ArrayList<String>();
	}
	
	/**
	 * Test a new word and value against the current champions.
	 * If the value is high or low enough, the word is stored
	 * among the appropriate champions (high or low).
	 */
	public void testContestant(String word, double value){
	
		// Calculate competing value as distance from point of no interest.
		double cValue = (value-pointOfNoInterest)*(value-pointOfNoInterest);
	
		// Test contestant.
		if(cVals.isEmpty()){
			// No competition. Just add the contestant.
			cVals.add(cValue);
			vals.add(value);
			words.add(word);
		}else{
			// Test the contestant against the current champions.
			int pos = 0;
			while(pos < cVals.size() && cValue < cVals.get(pos)){
				pos++;
			}
			cVals.add(pos,cValue);
			vals.add(pos,value);
			words.add(pos,word);
			if(cVals.size() > n){
				// Full house. Somebody has to go. Let's kick the weakest one.
				cVals.remove(n);
				vals.remove(n);
				words.remove(n);
			}
		}
	}
	
	public String getChampion(int pos){
		if(pos < words.size()){
			return words.get(pos);
		}
		return null;
	}
	
	public double getChampionValue(int pos){
		if(pos < vals.size()){
			return vals.get(pos);
		}
		return -1.0;
	}
	
	public int size(){
		return cVals.size();
	}

	/**
	 * Created just for testing class Champion.
	 */
	public static void main(String[] args) {
		Champion champ = new Champion(5);
		
		champ.testContestant("three", 0.3);
		champ.testContestant("one", 0.1);
		champ.testContestant("two", 0.2);
		champ.testContestant("six", 0.6);
		champ.testContestant("four", 0.4);
		champ.testContestant("five", 0.5);
		champ.testContestant("seven", 0.7);
		
		System.out.println("Champions:");
		for(int i = 0; i < champ.size(); i++){
			System.out.println(champ.getChampion(i));
		}
	}
	
	// Amount of champions in this container.
	private int n;

	// Value for point of no interest, i.e.,
	// when the probability of a word being spam
	// or not spam is the same.
	private double pointOfNoInterest = 0.5;
	
	// Arrays that contains the current champion words.
	ArrayList<Double> cVals; // competing values.
	ArrayList<Double> vals; // real values.
	ArrayList<String> words;
}