package de.tudarmstadt.gdi1.project.ana.caesar;

import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.exception.*;
import de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis;

/**
 * 
 * @author vamp
 * @version FINAL
 */
public class CaesarCryptAna implements CaesarCryptanalysis {
	
	private final double[] FACTORS = new double[]{0.5, 0.35, 0.15};
	

	
	/**
	 * returns the index of the smallest argument,
	 * if there are two or more equal entries, the smaller index will be returned 
	 * @param a an array of doubles
	 * @return the index of the minimum
	 */
	public int minIndex(double[] a){
		if(a == null || a.length == 0){
			return -1;
		}
		
		int result = 0;
		double currentMin = a[0];
		
		//iterates over the array to find the smallest entry
		for(int i = 0; i < a.length; i++){
			if(a[i] < currentMin){
				result = i;
				currentMin = a[i];
			}
		}
		return result;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution)
	 */
	@Override
	public Integer knownCiphertextAttack(String ciphertext, Distribution distribution) {
		
		if(distribution == null || distribution.getAlphabet() == null){
			throw new InvalidAlphabetException("A proper distribution with an alphabet which is different from null is required");
		}

		Alphabet alph = distribution.getAlphabet();
		int alphSize = alph.size();
		
		if(alphSize == 0){
			throw new InvalidAlphabetException("The alphabet does not contain any characters");
		}	
		if(ciphertext.equals("")){
			throw new IllegalArgumentException("Cannot determine a key to decrypt the empty string");
		}
		if(! alph.allows(ciphertext)){
			throw new IllegalArgumentException("The Ciphertext and the alphabet does not match");
		}	
		
		
		//the distribution of the characters in the ciphertext
		Distribution distr = new ImplDistribution(alph, ciphertext, 1);
		
		//represents the number of different characters in the ciphertext or alphabet of the language
		//cases, where there are less than three will be treated separately
		int anz = 0;
		
		char[] ciphChars = new char[3];
		char[] distrChars = new char[3]; 
		int[] differenceToFirstCipher = new int[3];

		for(int i = 0; i<3; i++){
			
			try {
				//writes the three characters from the cipher text which appear the most often into the ciphChars Array  
				ciphChars[i] = distr.getSorted(1).get(i).toCharArray()[0];
				
				//the same with the characters from the distribution of the underlying language
				distrChars[i] = distribution.getSorted(1).get(i).toCharArray()[0];

				
				//evaluates the difference between the indices of the the most frequently appearing character in the ciphertext
				//and the three most frequently appearing characters in the distribution
				//these three numbers will be the keys that are tested later
				differenceToFirstCipher[i] = alph.getIndex(ciphChars[0]) - alph.getIndex(distrChars[i]);	
				
				anz += 1;
			}
			//in the case that the cipher text or the the distribution of the alphabet does not contain enough characters, the loop is stopped 
			catch (IndexOutOfBoundsException e){
				break;
			}
		}
	
		
		//tests at most three possibilities:  
		//the most frequently used character in the ciphertext is either the first/second/third most frequently used character in the underlying distribution
		//the result will be the case, where the sum of differences to the expected frequencies is minimal
		
		//reads of the frequencies of the first/second and third character (in the expected distribution)
		double[] frequencyDistr = new double[anz];
		for(int i = 0; i<anz; i++){
			frequencyDistr[i] = distribution.getFrequency( Character.toString(distrChars[i]));
		}
		
		char[][] decryptChars = new char[anz][anz];
		double[] totalDifference = new double[anz];
		
		for(int i = 0; i<anz; i++){
			//decrypts the second and third character with the right key
			//the addition of the size of the alphabet and taking the remainder ensure that the number is bigger or equal to 0 smaller than alphSize
			decryptChars[0][i] = distrChars[i];
			for(int j = 1; j<anz; j++){
				decryptChars[j][i] = alph.getChar((alph.getIndex(ciphChars[j]) - differenceToFirstCipher[i] + alphSize) % alphSize);
			}
			

			//determines the differences of the frequencies in the ciphertext to the expected ones and evaluates a weighted average
			totalDifference[i] = 0;
			for(int j=0; j < anz; j++){
				totalDifference[i] += FACTORS[j]*  Math.abs(distribution.getFrequency(Character.toString(decryptChars[j][i])) - frequencyDistr[j]);
			}

		}
		
		
		//determines the best match
		int bestCase = minIndex(totalDifference);	
		//returns the key 						
		return (differenceToFirstCipher[bestCase] + alphSize) % alphSize;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownPlaintextAttack(java.lang.String, java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Alphabet)
	 */
	@Override
	public Integer knownPlaintextAttack(String ciphertext, String plaintext, Alphabet alph){
		if(ciphertext.equals("") || plaintext.equals("")){
			return 0;
		}
		char firstCiph = ciphertext.toCharArray()[0];
		char firstPlain = plaintext.toCharArray()[0];
		return (alph.getIndex(firstCiph) - alph.getIndex(firstPlain) + alph.size()) % alph.size();
	}
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public Integer knownCiphertextAttack(String ciphertext, Dictionary dictionary) {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public Integer knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dict) {
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownPlaintextAttack(java.lang.String, java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution)
	 */
	@Override
	public Integer knownPlaintextAttack(String ciphertext, String plaintext, Distribution distribution){
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis#knownPlaintextAttack(java.lang.String, java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public Integer knownPlaintextAttack(String ciphertext, String plaintext, Distribution distribution, Dictionary dictionary){
		
		return null;
	} 

}
