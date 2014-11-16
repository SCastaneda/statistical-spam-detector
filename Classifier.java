import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

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

		BufferedReader input;
		try{
			input = new BufferedReader(new FileReader("training.txt"));
		
		
			String line = input.readLine();
			String[] toks = line.split("\\s");
			
			nbad = Integer.parseInt(toks[0]);
			//System.out.println("nbad=" + nbad);
			ngood =  Integer.parseInt(toks[1]);
			//System.out.println("ngood=" + ngood);
			
			// calculate token probabilities.
			tokenProb = new HashMap<String,Double>();
			//for(int i = 0; 0 < tokens.size(); i++){
			line = input.readLine();
			while(line != null && line.length() > 0){
				toks = line.split("\\s");
				String token = toks[0];
				//System.out.println("token=" + token);
				double b = Integer.parseInt(toks[1]);
				//System.out.println("b=" + b);
				double g = 2*(Integer.parseInt(toks[2]));
				//System.out.println("g=" + g);
				
				double prob;
				if(b+g < 5){
					prob = defaultTokenProb;
				}else{
					prob = Math.min(1.0,b/nbad) / ( Math.min(1.0, g/ngood) + Math.min(1.0, b/nbad) );
					if (prob < 0.1) prob = 0.1;
					if (prob > 0.99) prob = 0.99;
					tokenProb.put(token,prob);
				}
				line = input.readLine();
			}
			input.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	
	public HashMap<String,Integer> parse(String email){
		SimpleParser sp = new SimpleParser();
		String[] tokens = sp.filter(email);
		return TokenCounter.count(tokens[0],tokens[1]);
	}
	
	/**
	 * Given a raw email string, this function classify such
	 * email as spam (1) or not spam (0).
	 */
	public int classify(String email){
	
		// Use parser to counts words on email.
		HashMap<String,Integer> tokenFreq = parse(email);
		
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
		
		// Print most interesting tokens
		// for(int i =0; i < champ.size(); i++){
		// 	System.out.println(champ.getChampion(i) + " " + champ.getChampionValue(i) + " " + tokenProb.get(champ.getChampion(i)) );
		// }

		// System.out.printf("\n");
		
		// Calculate overall probability
		double prod = 1; 
		double invProd = 1;
		for(int i =0; i < champ.size(); i++){
			prod *= champ.getChampionValue(i);
			invProd *= (1-champ.getChampionValue(i));
		}
		double overallProb = prod / (prod + invProd);
		// System.out.println("SPAM PROB=" + overallProb);

		// System.out.printf("\n\n");
		
		// Classify email as spam or not spam.
		if (overallProb > spamProbLimit){
			return 1;
		}
		return 0;
	}
	
	public double getSpamProb(String word){
		if(tokenProb.containsKey(word)){
			return tokenProb.get(word);
		}else{
			return defaultTokenProb;
		}
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
