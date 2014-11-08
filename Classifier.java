import java.util.*;

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
		// TODO: load training results, i.e., the file with 
		//       words and their frequencies.
		
		// TODO: calculate token probabilities.
		tokenProb = new HashMap<String,Double>;
	}
	
	/**
	 * Given a raw email string, this function classify such
	 * email as spam (TRUE) or not spam (FALSE).
	 */
	public boolean classify(String email){
		// Use parser to counts words on email.
		HashMap<String,Integer> tokenFreq = EmailParser.parseSingleEmail(email);
		
		// Find most interesting tokens.
		Champion champ = new Champion();
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
		
		// Calculate combined probability
		// TODO
		double cprob = 0.5;
		
		// Classify email as spam or not spam.
		if (cprob > spamProbLimit){
			return true;
		}
		return false;
	}

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
