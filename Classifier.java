import java.util.*;
import java.io.*;

/**
 * This class is in charge of classifying emails as spam or not spam.
 */
public class Classifier {
	
	/**
	 * Default constructor.
	 */
	public Classifier(){
		calculateTokenProbabilities();
	}
	
	/**
	 * Specify the probability for new tokens and the limit
	 * for spam classification.
	 */
	public Classifier(double defaultTokenProbability, double spamLimit){
		defaultTokenProb = defaultTokenProbability;
		spamProbLimit = spamLimit;
		calculateTokenProbabilities();
	}
	
	private void calculateTokenProbabilities(){
		
		Scanner input = null;
		try {
			input = new Scanner(new File("filename"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not open filename");
		}
		input.useDelimiter(" ");
		
		nbad = input.nextInt();
		ngood = input.nextInt();
		
		// calculate token probabilities.
		tokenProb = new HashMap<String,Double>;
		for(int i = 0; 0 < tokens.size(); i++){
			String token = input.next();
			double b = input.nextInt();
			double g = 2*(input.nextInt());
			
			double prob;
			if(b+g < 5){
				prob = defaultTokenProb;
			}else{
				prob = min(1.0,b/nbad) / ( min(1.0, g/ngood) + min(1.0, b/nbad) );
				if (prob < 0.1) prob = 0.1;
				if (prob > 0.99) prob = 0.99;
			}
			
			tokenProb.add(token,prob);
		}
	}
	
	/**
	 * Given a raw email string, this function classify such
	 * email as spam (TRUE) or not spam (FALSE).
	 */
	public int classify(String email){
		// Use parser to counts words on email.
		HashMap<String,Integer> tokenFreq = EmailParser.parseSingleEmail(email);
		Scanner input = null;
		try {
			input = new Scanner(new File("filename"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not open filename");
		}
		input.useDelimiter(" ");
		
		// Find most interesting tokens.
		Champion champ = new Champion(15);
		for (String token : tokenFreq.keySet()){
			// Find probability for this token
			double prob = 0.0;
			if(tokenProb.containsKey(token)){
				prob = tokenProb.get(token);
			}else{
				prob = defaultTokenProb;
			}
			
			// Test if this token is interesting enough.
			champ.testContestant(token, prob);
		}
		
		// Calculate overall probability
		double cprob = 0.5;
		double prod = 1; 
		double invProd = 1;
		for(int i =0; i < champ.size(); i++){
			prod *= champ.getChampionValue(i);
			invProd *= (1-champ.getChampionValue(i));
		}
		double overallProb = prod / (prod + invProd);
		
		// Classify email as spam or not spam.
		if (overallProb > spamProbLimit){
			return 1;
		}
		return 0;
	}
	
	private int ngood, nbad;

	/**
	 * Probability assigned to a token not encountered during training.
	 */
	private double defaultTokenProb = 0.4;
	
	/**
	 * Emails with a combined probability higher than
	 * spamProbLimit will be classified as spam.
	 */
	private double spamProbLimit = 0.9;
	
	/**
	 * Contains the calculated probability of being spam
	 * for each token encountered during training.
	 */
	private HashMap<String,Double> tokenProb;
}
