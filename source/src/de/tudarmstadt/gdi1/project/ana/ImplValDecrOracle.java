package de.tudarmstadt.gdi1.project.ana;

import java.util.Iterator;

import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.alphabet.*;

/**
 * checks whether a string only contains words from a given dictionary
 * @author vamp
 * @version FINAL
 */
public class ImplValDecrOracle implements ValidateDecryptionOracle {

	private Dictionary dictionary;
	private int maxWordSize;
	
	/**
	 * Constructs a new ImplValDecrOracle
	 * @param distr a distribution of a typical text
	 * @param dict a dictionary of all words of a language
	 */
	public ImplValDecrOracle(Distribution distr, Dictionary dict){
		if(dict == null || dict.size() == 0){
			throw new IllegalArgumentException("Today the Oracle of Delphi is mad at you for trying to use an empty dictionary");			
		}
		
		
		dictionary = dict;
		
		//evaluates the word with the biggest length in the dictionary
		int currentMaxLength = 0;
		Iterator<String> it = dict.iterator();
		while(it.hasNext()){
			currentMaxLength = Math.max(currentMaxLength,  it.next().length());
		}
		maxWordSize = currentMaxLength;
		
	}
	
	
	@Override
	public boolean isCorrect(String plaintext) {
		
		//base cases
		if (plaintext.equals("") || plaintext == null || dictionary.contains(plaintext)){
			return true;
		} else {
			
			
			//tries to find a solution via backtracking
			for (int i = 1; i <= Math.min(maxWordSize, plaintext.length()); i++){
				
				if (dictionary.contains(plaintext.substring(0, i))){
					if (isCorrect(plaintext.substring(i))){ //this would mean that a solution is found
						return true;
					} 
				}	
			}
			return false;
		}
	}
	
}
